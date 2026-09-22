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

@Composable
fun EventBreakdown() {

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

        BreakdownRow(
            event = "INSTALL",
            count = "12",
            percentage = "9.38%",
            color = SuccessGreen
        )

        BreakdownRow(
            event = "VISIT",
            count = "26",
            percentage = "20.31%",
            color = PrimaryBlue
        )

        BreakdownRow(
            event = "ADD_TO_CART",
            count = "37",
            percentage = "28.91%",
            color = Color(0xFF5B20E8)
        )

        BreakdownRow(
            event = "PURCHASE",
            count = "53",
            percentage = "41.41%",
            color = RetryOrange
        )
    }
}

@Preview
@Composable
private fun EventBreakdownPreview() {
    EventBreakdown()
}
