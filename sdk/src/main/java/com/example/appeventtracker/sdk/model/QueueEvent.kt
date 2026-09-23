package com.example.appeventtracker.sdk.model

data class QueueEvent(
    val id: Long,
    val type: String,
    val timestamp: Long,
    val status: String,
    val retryCount: Int,
    val nextRetryAt: Long?
)