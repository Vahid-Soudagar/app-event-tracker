package com.example.appeventtracker.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EventQueueScreen(modifier: Modifier = Modifier) {
    Text(
        text = "Event Queue Screen",
        modifier = modifier
    )
}


@Preview
@Composable
private fun EventQueueScreenPreview() {
    EventQueueScreen()
}