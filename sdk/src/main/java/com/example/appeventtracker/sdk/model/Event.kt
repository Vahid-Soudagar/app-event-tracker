package com.example.appeventtracker.sdk.model

import java.sql.Timestamp

data class Event (
    val eventType: EventType,
    val timeStamp: Long,
    val sessionId: String,
    val installationId: String
)