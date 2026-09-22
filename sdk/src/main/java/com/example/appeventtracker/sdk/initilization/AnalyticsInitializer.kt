package com.example.appeventtracker.sdk.initilization

import android.content.Context
import androidx.startup.Initializer
import com.example.appeventtracker.sdk.EventProcessor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class AnalyticsInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            EventProcessor.processPending(context.applicationContext)
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> {
        return emptyList()
    }
}