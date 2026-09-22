package com.example.appeventtracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appeventtracker.ui.theme.BorderColor
import com.example.appeventtracker.ui.theme.PrimaryBlue
import com.example.appeventtracker.ui.theme.RetryOrange
import com.example.appeventtracker.ui.theme.SuccessGreen
import com.example.appeventtracker.ui.theme.TextSecondary

@Composable
fun EventStatCard(
    event: String = "INSTALL",
    count: String = "102",
    color: androidx.compose.ui.graphics.Color = Color(0xFF00A63C),
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(84.dp)
            .border(
                width = 1.dp,
                color = BorderColor,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(24.dp)
                .background(
                    color = color,
                    shape = CircleShape
                )
        )

        Column(
            modifier = Modifier.padding(start = 10.dp)
        ) {

            Text(
                text = event,
                fontSize = 10.sp,
                color = TextSecondary
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = count,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = when (event) {
                    "INSTALL" -> SuccessGreen
                    "VISIT" -> PrimaryBlue
                    "ADD_TO_CART" -> Color(0xFF5B20E8)
                    "PURCHASE" -> RetryOrange
                    else -> PrimaryBlue
                }
            )
        }
    }
}

@Preview
@Composable
private fun EventStatCardPreview() {
    EventStatCard()
}