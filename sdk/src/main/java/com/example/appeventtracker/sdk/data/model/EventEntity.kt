package com.example.appeventtracker.sdk.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.appeventtracker.sdk.model.EventStatus
import com.example.appeventtracker.sdk.model.EventType

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "event_type")
    val eventType: EventType,

    @ColumnInfo(name = "time_stamp")
    val timeStamp: Long,

    @ColumnInfo(name = "session_id")
    val sessionId: String,

    @ColumnInfo(name = "installation_id")
    val installationId: String,

    @ColumnInfo(name = "status")
    val status: EventStatus,

    @ColumnInfo(name = "retry_count")
    val retryCount: Int = 0
)