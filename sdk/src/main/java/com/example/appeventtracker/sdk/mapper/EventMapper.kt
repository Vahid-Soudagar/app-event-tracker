package com.example.appeventtracker.sdk.mapper

import com.example.appeventtracker.sdk.data.model.EventEntity
import com.example.appeventtracker.sdk.model.QueueEvent


fun EventEntity.toQueueEvent(): QueueEvent {
    return QueueEvent(
        id = id,
        type = eventType.name,
        timestamp = timeStamp,
        status = status.name,
        retryCount = retryCount,
        nextRetryAt = nextRetryAt
    )
}