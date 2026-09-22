package com.example.appeventtracker.sdk

import android.content.Context
import android.util.Log
import com.example.appeventtracker.sdk.data.database.DatabaseProvider
import com.example.appeventtracker.sdk.model.EventStatus
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

object EventProcessor {

    suspend fun processPending(context: Context) {

        val database = DatabaseProvider.getDatabase(context)
        val dao = database.eventDao()

        val pendingEvents = dao.getPendingEvents()

        Log.d(
            "EventProcessor",
            "Pending events count = ${pendingEvents.size}"
        )

        pendingEvents.forEach { event ->

            val randomDelay = Random.nextLong(
                1_000L,
                5_001L
            )

            Log.d(
                "EventProcessor",
                "Waiting ${randomDelay}ms before processing $event"
            )

            delay(randomDelay.milliseconds)

            Log.d(
                "EventProcessor",
                "Ingestion attempt: $event"
            )

            val isSuccess = Random.nextInt(100) < 80
            if (isSuccess) {
                val processedEvent = event.copy(status = EventStatus.PROCESSED)
                dao.update(processedEvent)
                Log.d(
                    "EventProcessor",
                    "Ingestion SUCCESS: $processedEvent"
                )
            } else {
                val failedEvent = event.copy(
                    status = EventStatus.FAILED
                )
                dao.update(failedEvent)
                Log.d(
                    "EventProcessor",
                    "Ingestion FAILED: $failedEvent"
                )
            }
        }
    }
}