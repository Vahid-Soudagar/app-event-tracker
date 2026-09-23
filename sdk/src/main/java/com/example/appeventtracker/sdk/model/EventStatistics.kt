package com.example.appeventtracker.sdk.model

data class EventStatistics(
    val totalProcessed: Int,
    val uniqueVisits: Int,
    val countsByType: Map<String, Int>
)