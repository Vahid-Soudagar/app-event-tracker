package com.example.appeventtracker.sdk

import android.content.Context
import android.util.Log
import com.example.appeventtracker.sdk.model.Event
import com.example.appeventtracker.sdk.model.EventRequest
import kotlinx.serialization.json.Json

object Analytics {

    fun reportEvents(json: String, context: Context) {
        Log.d("DebugTag", "Json is $json")
        val request = Json.decodeFromString<EventRequest>(json)
        Log.d("DebugTag", "Request $request")
        val sessionId = SessionManager.getSessionId()
        val installationId = InstallationManager.getInstallationId(context = context)
        val events = request.events.map { eventType ->
            Event(
                eventType = eventType,
                timeStamp = System.currentTimeMillis(),
                sessionId = sessionId,
                installationId = installationId
            )
        }
        Log.d("DebugTag", "Events $events")
    }
}