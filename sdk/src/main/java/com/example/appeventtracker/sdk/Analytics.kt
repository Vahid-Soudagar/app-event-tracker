package com.example.appeventtracker.sdk

import android.content.Context
import android.util.Log
import com.example.appeventtracker.sdk.data.database.DatabaseProvider
import com.example.appeventtracker.sdk.data.model.EventEntity
import com.example.appeventtracker.sdk.mapper.toQueueEvent
import com.example.appeventtracker.sdk.model.Event
import com.example.appeventtracker.sdk.model.EventRequest
import com.example.appeventtracker.sdk.model.EventStatistics
import com.example.appeventtracker.sdk.model.EventStatus
import com.example.appeventtracker.sdk.model.EventType
import com.example.appeventtracker.sdk.model.QueueEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.json.Json

object Analytics {

    private val insertMutex = Mutex()

    fun reportEvents(json: String, context: Context) {
        Log.d("DebugTag", "Json is $json")
        val request = try {
            Json.decodeFromString<EventRequest>(json)
        } catch (e: IllegalArgumentException) {
            // SerializationException extends IllegalArgumentException
            Log.e("DebugTag", "Invalid events JSON, nothing reported: $json", e)
            return
        }
        Log.d("DebugTag", "Request $request")

        val sessionId = SessionManager.getSessionId()
        val installationId = InstallationManager.getInstallationId(context = context)

        val eventTypes = request.events.mapNotNull { name ->
            EventType.entries.find { it.name == name } ?: run {
                Log.w("DebugTag", "Unknown event type skipped: $name")
                null
            }
        }

        val enrichedEvents = eventTypes.map { eventType ->
            Event(
                eventType = eventType,
                timeStamp = System.currentTimeMillis(),
                sessionId = sessionId,
                installationId = installationId
            )
        }
        Log.d("DebugTag", "Events $enrichedEvents")


        CoroutineScope(Dispatchers.IO).launch {
            val database = DatabaseProvider.getDatabase(context)
            val dao = database.eventDao()

            // Concurrent reportEvents calls must not both pass the dedup check
            insertMutex.withLock {
                enrichedEvents.forEach { event ->
                    val shouldProcess = when (event.eventType) {
                        EventType.INSTALL -> {
                            !dao.isInstalledProcessed(
                                eventType = EventType.INSTALL,
                                installationId = event.installationId
                            )
                        }
    
                        EventType.VISIT -> {
                            !dao.isVisitProcessed(
                                eventType = EventType.VISIT,
                                sessionId = event.sessionId
                            )
                        }
    
                        EventType.ADD_TO_CART -> {
                            true
                        }
    
                        EventType.PURCHASE -> {
                            true
                        }
                    }
    
                    if (shouldProcess) {
                        val eventEntity = EventEntity(
                            eventType = event.eventType,
                            timeStamp = event.timeStamp,
                            sessionId = event.sessionId,
                            installationId = event.installationId,
                            status = EventStatus.PENDING
                        )
    
                        dao.insert(event = eventEntity)
                        Log.d(
                            "DebugTag",
                            "Inserted: $eventEntity"
                        )
                    } else {
                        Log.d(
                            "DebugTag",
                            "Duplicate skipped: $event"
                        )
                    }
                }
            }
            EventProcessor.processPending(context = context)
        }
    }

    fun retryPending(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            EventProcessor.processPending(context = context.applicationContext)
        }
    }

    fun observeQueueEvents(context: Context): Flow<List<QueueEvent>> {
        return DatabaseProvider
            .getDatabase(context)
            .eventDao()
            .observeAllEvents()
            .map { events ->
                events.map { it.toQueueEvent() }
            }
    }

    fun observeStatistics(context: Context): Flow<EventStatistics> {
        return DatabaseProvider
            .getDatabase(context)
            .eventDao()
            .observeProcessedEvents()
            .map { events ->
                val countsByType = EventType.entries.associate { type ->
                    type.name to events.count { it.eventType == type }
                }
                val uniqueVisits = events
                    .filter { it.eventType == EventType.VISIT }
                    .distinctBy { it.sessionId }
                    .size

                EventStatistics(
                    totalProcessed = events.size,
                    uniqueVisits = uniqueVisits,
                    countsByType = countsByType
                )
            }
    }
}