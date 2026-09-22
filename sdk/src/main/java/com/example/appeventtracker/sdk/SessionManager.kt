package com.example.appeventtracker.sdk

import java.util.UUID

internal object SessionManager {
    private val sessionId = UUID.randomUUID().toString()

    fun getSessionId(): String = sessionId
}