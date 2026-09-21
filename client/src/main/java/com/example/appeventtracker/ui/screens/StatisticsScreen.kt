package com.example.appeventtracker.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun StatisticsScreen(modifier: Modifier = Modifier) {
    Text(
        text = "Statistics Screen",
        modifier = modifier
    )
}

@Preview
@Composable
private fun StatisticsScreenPreview() {
    StatisticsScreen()
}