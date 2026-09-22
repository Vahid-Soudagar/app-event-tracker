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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appeventtracker.ui.components.EventBreakdown
import com.example.appeventtracker.ui.components.EventStatCard
import com.example.appeventtracker.ui.components.SummaryCard
import com.example.appeventtracker.ui.theme.*

@Composable
fun StatisticsScreen(
    modifier: Modifier = Modifier
) {

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
                    value = "128",
                    modifier = Modifier.weight(1f)
                )

                SummaryCard(
                    title = "Total Visits\n(Unique Session)",
                    value = "26",
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
                    count = "12",
                    color = InstallLight,
                    modifier = Modifier.weight(1f)
                )

                EventStatCard(
                    event = "VISIT",
                    count = "26",
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
                    count = "37",
                    color = CartLight,
                    modifier = Modifier.weight(1f)
                )

                EventStatCard(
                    event = "PURCHASE",
                    count = "53",
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

            EventBreakdown()
        }
    }
}

@Preview
@Composable
private fun StatisticsScreenPreview() {
    StatisticsScreen()
}