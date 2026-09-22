package com.example.appeventtracker.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BreakdownHeader() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "Event",
            modifier = Modifier.weight(1f),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Count",
            modifier = Modifier.weight(0.6f),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Percentage",
            modifier = Modifier.weight(0.8f),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
private fun BreakdownHeaderPreview() {
    BreakdownHeader()
}