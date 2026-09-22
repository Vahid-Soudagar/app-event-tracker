package com.example.appeventtracker.util

import com.example.appeventtracker.model.QueueEvent

object MockDataGenerator {

    fun generateQueueEvents(): List<QueueEvent> {
        return listOf(
            QueueEvent(
                type = "INSTALL",
                time = "10:29:41 AM",
                status = "Processed ✓"
            ),
            QueueEvent(
                type = "VISIT",
                time = "10:29:45 AM",
                status = "Processed ✓"
            ),
            QueueEvent(
                type = "ADD_TO_CART",
                time = "10:29:48 AM",
                status = "Processing"
            ),
            QueueEvent(
                type = "PURCHASE",
                time = "10:29:50 AM",
                status = "Retrying in 2s"
            ),
            QueueEvent(
                type = "VISIT",
                time = "10:29:55 AM",
                status = "Processed ✓"
            ),
            QueueEvent(
                type = "PURCHASE",
                time = "10:29:59 AM",
                status = "Retrying in 3s"
            ),
            QueueEvent(
                type = "ADD_TO_CART",
                time = "10:30:01 AM",
                status = "Processed ✓"
            )
        )
    }
}