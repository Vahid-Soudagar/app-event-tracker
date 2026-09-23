package com.example.appeventtracker.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appeventtracker.ui.theme.BorderColor
import com.example.appeventtracker.ui.theme.PrimaryBlue
import com.example.appeventtracker.ui.theme.RetryOrange
import com.example.appeventtracker.ui.theme.SuccessGreen
import java.util.Locale

@Composable
fun EventBreakdown(
    countsByType: Map<String, Int> = emptyMap(),
    total: Int = 0
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = BorderColor,
                shape = RoundedCornerShape(8.dp)
            )
    ) {

        BreakdownHeader()

        HorizontalDivider()

        breakdownEvents.forEach { (event, color) ->
            val count = countsByType[event] ?: 0
            BreakdownRow(
                event = event,
                count = count.toString(),
                percentage = formatPercentage(count, total),
                color = color
            )
        }
    }
}

private val breakdownEvents = listOf(
    "INSTALL" to SuccessGreen,
    "VISIT" to PrimaryBlue,
    "ADD_TO_CART" to Color(0xFF5B20E8),
    "PURCHASE" to RetryOrange
)

private fun formatPercentage(count: Int, total: Int): String {
    val percentage = if (total == 0) 0.0 else count * 100.0 / total
    return String.format(Locale.US, "%.2f%%", percentage)
}

@Preview
@Composable
private fun EventBreakdownPreview() {
    EventBreakdown(
        countsByType = mapOf(
            "INSTALL" to 12,
            "VISIT" to 26,
            "ADD_TO_CART" to 37,
            "PURCHASE" to 53
        ),
        total = 128
    )
}