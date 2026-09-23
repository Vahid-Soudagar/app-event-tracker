package com.example.appeventtracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appeventtracker.sdk.Analytics
import com.example.appeventtracker.ui.components.EventBreakdown
import com.example.appeventtracker.ui.components.EventStatCard
import com.example.appeventtracker.ui.components.SummaryCard
import com.example.appeventtracker.ui.theme.*

@Composable
fun StatisticsScreen(
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val statistics by remember(context) {
        Analytics.observeStatistics(context = context)
    }.collectAsState(initial = null)

    val total = statistics?.totalProcessed ?: 0
    val uniqueVisits = statistics?.uniqueVisits ?: 0
    val countsByType = statistics?.countsByType.orEmpty()

    fun countOf(event: String) = (countsByType[event] ?: 0).toString()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = 12.dp,
            vertical = 8.dp
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        item {

            Text(
                text = "Statistics",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            // Summary cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                SummaryCard(
                    title = "Total Events Processed",
                    value = total.toString(),
                    modifier = Modifier.weight(1f)
                )

                SummaryCard(
                    title = "Total Visits\n(Unique Session)",
                    value = uniqueVisits.toString(),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(
                modifier = Modifier.height(2.dp)
            )

            // Event statistics
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                EventStatCard(
                    event = "INSTALL",
                    count = countOf("INSTALL"),
                    color = InstallLight,
                    modifier = Modifier.weight(1f)
                )

                EventStatCard(
                    event = "VISIT",
                    count = countOf("VISIT"),
                    color = VisitLight,
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                EventStatCard(
                    event = "ADD_TO_CART",
                    count = countOf("ADD_TO_CART"),
                    color = CartLight,
                    modifier = Modifier.weight(1f)
                )

                EventStatCard(
                    event = "PURCHASE",
                    count = countOf("PURCHASE"),
                    color = PurchaseLight,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Event Breakdown",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            EventBreakdown(
                countsByType = countsByType,
                total = total
            )
        }
    }
}

@Preview
@Composable
private fun StatisticsScreenPreview() {
    StatisticsScreen()
}