package com.example.appeventtracker.sdk.model

import kotlinx.serialization.Serializable


@Serializable
data class EventRequest(
    val events: List<String>
)