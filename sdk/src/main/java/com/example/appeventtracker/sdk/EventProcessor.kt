package com.example.appeventtracker.sdk

import android.content.Context
import android.util.Log
import com.example.appeventtracker.sdk.data.database.DatabaseProvider
import com.example.appeventtracker.sdk.model.EventStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

internal object EventProcessor {

    private const val MAX_BACKOFF_MS = 30_000L

    // Startup and every reportEvents call trigger processing; serialize them
    // so the same row is never picked up by two runs at once.
    private val mutex = Mutex()

    suspend fun processPending(context: Context) = mutex.withLock {

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

                // Wait out the backoff scheduled by a previous failure
                val retryWait = (currentEvent.nextRetryAt ?: 0L) - System.currentTimeMillis()
                if (retryWait > 0) {
                    delay(retryWait.milliseconds)
                }

                currentEvent = currentEvent.copy(
                    status = EventStatus.PROCESSING,
                    nextRetryAt = null
                )
                dao.update(currentEvent)

                // Simulated network latency
                val randomDelay = Random.nextLong(
                    1_000L,
                    5_001L
                )

                Log.d(
                    "EventProcessor",
                    "Ingestion attempt after ${randomDelay}ms: $currentEvent"
                )

                delay(randomDelay.milliseconds)

                val isSuccess = Random.nextInt(100) < 80
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
                    val retryCount = currentEvent.retryCount + 1
                    val backoff = (1_000L shl retryCount.coerceAtMost(5)).coerceAtMost(MAX_BACKOFF_MS)
                    currentEvent = currentEvent.copy(
                        status = EventStatus.FAILED,
                        retryCount = retryCount,
                        nextRetryAt = System.currentTimeMillis() + backoff
                    )
                    dao.update(currentEvent)
                    Log.d(
                        "EventProcessor",
                        "Ingestion FAILED, retry in ${backoff}ms: $currentEvent"
                    )
                }
            }
        }
    }
}