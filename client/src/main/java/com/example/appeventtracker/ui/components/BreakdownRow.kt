package com.example.appeventtracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appeventtracker.ui.theme.SuccessGreen

@Composable
fun BreakdownRow(
    event: String = "INSTALL",
    count: String = "102",
    percentage: String = "98.5",
    color: androidx.compose.ui.graphics.Color = Color(0xFF00A63C)
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 10.dp,
                vertical = 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(6.dp)
                    .background(
                        color = color,
                        shape = CircleShape
                    )
            )

            Text(
                text = event,
                modifier = Modifier.padding(start = 6.dp),
                fontSize = 10.sp
            )
        }

        Text(
            text = count,
            modifier = Modifier.weight(0.6f),
            fontSize = 10.sp
        )

        Text(
            text = percentage,
            modifier = Modifier.weight(0.8f),
            fontSize = 10.sp
        )
    }
}

@Preview
@Composable
private fun BreakdownRowPreview() {
    BreakdownRow()

}