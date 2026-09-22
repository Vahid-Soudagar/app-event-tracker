package com.example.appeventtracker.sdk

import android.content.Context
import android.util.Log
import com.example.appeventtracker.sdk.model.Event
import com.example.appeventtracker.sdk.model.EventRequest
import kotlinx.serialization.json.Json

object Analytics {

    private val processedInstallations = mutableSetOf<String>()
    private val processedVisitSessions = mutableSetOf<String>()

    fun reportEvents(json: String, context: Context) {
        Log.d("DebugTag", "Json is $json")
        val request = Json.decodeFromString<EventRequest>(json)
        Log.d("DebugTag", "Request $request")
        val sessionId = SessionManager.getSessionId()
        val installationId = InstallationManager.getInstallationId(context = context)
        val enrichedEvents = request.events.map { eventType ->
            Event(
                eventType = eventType,
                timeStamp = System.currentTimeMillis(),
                sessionId = sessionId,
                installationId = installationId
            )
        }
        Log.d("DebugTag", "Events $enrichedEvents")

        val validEvents = enrichedEvents.filter { event ->
            when (event.eventType) {
                "INSTALL" -> {
                    if (processedInstallations.contains(event.installationId)) {
                        false
                    } else {
                        processedInstallations.add(event.installationId)
                        true
                    }
                }

                "VISIT" -> {
                    if (processedVisitSessions.contains(event.sessionId)) {
                        false
                    } else {
                        processedVisitSessions.add(event.sessionId)
                        true
                    }
                }

                else -> {
                    true
                }
            }
        }

        Log.d("DebugTag", "Valid Events $validEvents")
    }
}