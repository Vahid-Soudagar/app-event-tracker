package com.example.appeventtracker.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appeventtracker.ui.theme.BorderColor
import com.example.appeventtracker.ui.theme.PrimaryBlue
import com.example.appeventtracker.ui.theme.TextSecondary

@Composable
fun SummaryCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .height(94.dp)
            .border(
                width = 1.dp,
                color = BorderColor,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(10.dp)
    ) {

        Text(
            text = title,
            fontSize = 10.sp,
            color = TextSecondary
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = value,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryBlue
        )
    }
}

@Preview
@Composable
private fun SummaryCardPreview() {
    SummaryCard(title = "Summary Card", value = "123")
}