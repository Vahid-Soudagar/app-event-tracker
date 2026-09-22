package com.example.appeventtracker.sdk

import android.content.Context
import android.util.Log
import com.example.appeventtracker.sdk.data.database.DatabaseProvider
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
        }
    }
}