package com.example.appeventtracker.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appeventtracker.sdk.Analytics
import com.example.appeventtracker.ui.components.MockEventItem
import com.example.appeventtracker.ui.theme.TextSecondary

// Button label to SDK event type name
private val mockEventButtons = listOf(
    "INSTALL" to "INSTALL",
    "VISIT" to "VISIT",
    "ADD TO CART" to "ADD_TO_CART",
    "PURCHASE" to "PURCHASE"
)

@Composable
fun MockDataScreen(
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val mockEvents = remember { mutableStateListOf<String>() }

    Column(
        modifier = modifier.fillMaxSize()
    ) {

        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Mock Data",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "${mockEvents.size} queued",
                fontSize = 12.sp,
                color = TextSecondary
            )
        }

        // Event buttons, two per row
        mockEventButtons.chunked(2).forEach { rowButtons ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowButtons.forEach { (label, eventType) ->
                    OutlinedButton(
                        onClick = {
                            mockEvents.add(eventType)
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = label,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Queued Mock Events",
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (mockEvents.isEmpty()) {
                Text(
                    text = "No mock events added",
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            } else {
                LazyColumn {
                    itemsIndexed(mockEvents) { index, eventType ->
                        MockEventItem(
                            position = index + 1,
                            eventType = eventType
                        )
                    }
                }
            }
        }

        // Actions
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            OutlinedButton(
                onClick = {
                    mockEvents.clear()
                },
                enabled = mockEvents.isNotEmpty(),
                modifier = Modifier.weight(1f)
            ) {
                Text("Clear")
            }

            Button(
                onClick = {
                    Analytics.reportEvents(
                        json = buildEventsJson(mockEvents),
                        context = context
                    )
                    mockEvents.clear()
                },
                enabled = mockEvents.isNotEmpty(),
                modifier = Modifier.weight(1f)
            ) {
                Text("Report Events")
            }
        }
    }
}

private fun buildEventsJson(events: List<String>): String {
    val eventsArray = events.joinToString(separator = ", ") { "\"$it\"" }
    return """{ "events": [$eventsArray] }"""
}

@Preview(showBackground = true)
@Composable
private fun MockDataScreenPreview() {
    MockDataScreen()
}