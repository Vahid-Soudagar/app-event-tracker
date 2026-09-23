package com.example.appeventtracker.sdk

import android.content.Context
import android.util.Log
import com.example.appeventtracker.sdk.data.database.DatabaseProvider
import com.example.appeventtracker.sdk.data.model.EventEntity
import com.example.appeventtracker.sdk.mapper.toQueueEvent
import com.example.appeventtracker.sdk.model.Event
import com.example.appeventtracker.sdk.model.EventRequest
import com.example.appeventtracker.sdk.model.EventStatus
import com.example.appeventtracker.sdk.model.EventType
import com.example.appeventtracker.sdk.model.QueueEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

object Analytics {

    fun reportEvents(json: String, context: Context) {
        Log.d("DebugTag", "Json is $json")
        val request = Json.decodeFromString<EventRequest>(json)
        Log.d("DebugTag", "Request $request")

        val sessionId = SessionManager.getSessionId()
        val installationId = InstallationManager.getInstallationId(context = context)

        val enrichedEvents = request.events.map { eventType ->
            Event(
                eventType = EventType.valueOf(eventType),
                timeStamp = System.currentTimeMillis(),
                sessionId = sessionId,
                installationId = installationId
            )
        }
        Log.d("DebugTag", "Events $enrichedEvents")


        CoroutineScope(Dispatchers.IO).launch {
            val database = DatabaseProvider.getDatabase(context)
            val dao = database.eventDao()

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
}