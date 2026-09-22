package com.example.appeventtracker.sdk.data.converter

import androidx.room.TypeConverter
import com.example.appeventtracker.sdk.model.EventStatus
import com.example.appeventtracker.sdk.model.EventType

class EventConverter {

    @TypeConverter
    fun fromEventType(type: EventType): String {
        return type.name
    }

    @TypeConverter
    fun toEventType(value: String): EventType {
        return EventType.valueOf(value)
    }

    @TypeConverter
    fun fromEventStatus(status: EventStatus): String {
        return status.name
    }

    @TypeConverter
    fun toEventStatus(value: String): EventStatus {
        return EventStatus.valueOf(value)
    }
}