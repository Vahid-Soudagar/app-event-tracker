package com.example.appeventtracker.sdk.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.appeventtracker.sdk.data.model.EventEntity
import com.example.appeventtracker.sdk.model.EventType
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Insert
    suspend fun insertAll(events: List<EventEntity>)

    @Insert
    suspend fun insert(event: EventEntity)

    @Update
    suspend fun update(event: EventEntity)

    @Query("""
        SELECT EXISTS (
            SELECT 1 FROM events
            WHERE event_type = :eventType 
            AND installation_id = :installationId 
        )
    """)
    suspend fun isInstalledProcessed(
        eventType: EventType,
        installationId: String
    ) : Boolean

    @Query("""
        SELECT EXISTS(
            SELECT 1 FROM events
            WHERE event_type = :eventType
            AND session_id = :sessionId
        )
    """)
    suspend fun isVisitProcessed(
        eventType: EventType,
        sessionId: String
    ): Boolean

    @Query("""
        SELECT * FROM events
        WHERE status IN ('PENDING', 'PROCESSING', 'FAILED')
        ORDER BY id ASC
    """)
    suspend fun getPendingEvents(): List<EventEntity>

    @Query("""
        SELECT * FROM events
        ORDER BY id ASC
    """)
    fun observeAllEvents(): Flow<List<EventEntity>>
}