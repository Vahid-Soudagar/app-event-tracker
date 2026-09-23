package com.example.appeventtracker.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appeventtracker.sdk.Analytics
import com.example.appeventtracker.ui.components.EventItem

@Composable
fun EventQueueScreen(
    modifier: Modifier = Modifier
) {


    val selectedTab = remember { mutableIntStateOf(0) }

    val context = LocalContext.current
    val queueEvents by remember(context) {
        Analytics.observeQueueEvents(context = context)
    }.collectAsState(initial = emptyList())

    Column(
        modifier = modifier.fillMaxSize()
    )
    {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Event Queue",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            IconButton(
                onClick = {
                    Analytics.retryPending(context = context)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh"
                )
            }

        }

        TabRow(
            selectedTabIndex = selectedTab.intValue,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {

            Tab(
                selected = selectedTab.intValue == 0,
                onClick = {
                    selectedTab.intValue = 0
                },
                text = {
                    Text(
                        text = "IN PROGRESS",
                        fontSize = 12.sp
                    )
                }
            )

            Tab(
                selected = selectedTab.intValue == 1,
                onClick = {
                    selectedTab.intValue = 1
                },
                text = {
                    Text(
                        text = "FAILED / RETRYING",
                        fontSize = 12.sp
                    )
                }
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        val filteredEvents = if (selectedTab.intValue == 0) {
            queueEvents
        } else {
            // Failed / retrying = currently FAILED, or already failed once and now being retried
            queueEvents.filter {
                it.status == "FAILED" || it.retryCount > 0
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(filteredEvents, key = { it.id }) { event ->
                EventItem(event)
            }
        }
    }
}

@Preview
@Composable
private fun EventQueueScreenPreview() {
    EventQueueScreen()
}
