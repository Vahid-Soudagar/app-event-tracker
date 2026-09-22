package com.example.appeventtracker.sdk

import android.content.Context
import android.util.Log
import com.example.appeventtracker.sdk.data.database.DatabaseProvider
import com.example.appeventtracker.sdk.model.EventStatus
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

internal object EventProcessor {

    suspend fun processPending(context: Context) {

        val database = DatabaseProvider.getDatabase(context)
        val dao = database.eventDao()

        val pendingEvents = dao.getPendingEvents()

        Log.d(
            "EventProcessor",
            "Pending events count = ${pendingEvents.size}"
        )

        pendingEvents.forEach { event ->

            var currentEvent = event

            while (currentEvent.status != EventStatus.PROCESSED) {
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

                val isSuccess = Random.nextInt(100) < 20
                if (isSuccess) {

                    currentEvent = currentEvent.copy(
                        status = EventStatus.PROCESSED
                    )
                    dao.update(currentEvent)
                    Log.d(
                        "EventProcessor",
                        "Ingestion SUCCESS: $currentEvent"
                    )
                } else {
                    currentEvent = currentEvent.copy(
                        status = EventStatus.FAILED,
                        retryCount = currentEvent.retryCount + 1
                    )
                    dao.update(currentEvent)
                    Log.d(
                        "EventProcessor",
                        "Ingestion FAILED: $currentEvent"
                    )
                }
            }
        }
    }
}