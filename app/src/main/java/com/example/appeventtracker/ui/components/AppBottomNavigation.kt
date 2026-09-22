package com.example.appeventtracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appeventtracker.ui.theme.BorderColor

@Composable
fun AppBottomNavigation(
    selectedScreen: Int = 0,
    onScreenSelected: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            BottomNavItem(
                text = "Queue",
                selected = selectedScreen == 0,
                onClick = {
                    onScreenSelected(0)
                },
                modifier = Modifier.weight(1f)
            )

            BottomNavItem(
                text = "Statistics",
                selected = selectedScreen == 1,
                onClick = {
                    onScreenSelected(1)
                },
                modifier = Modifier.weight(1f)
            )

            BottomNavItem(
                text = "Mock Data",
                selected = selectedScreen == 2,
                onClick = {
                    onScreenSelected(2)
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AppBottomNavigationPreview() {
    AppBottomNavigation() {

    }
}