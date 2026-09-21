package com.example.appeventtracker.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.appeventtracker.ui.components.AppBottomNavigation
import com.example.appeventtracker.ui.components.AppTopBar
import com.example.appeventtracker.ui.screens.EventQueueScreen
import com.example.appeventtracker.ui.screens.StatisticsScreen

@Composable
fun App(modifier: Modifier = Modifier) {

    var selectedScreen by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            AppTopBar()
        },
        bottomBar = {
            AppBottomNavigation(
                selectedScreen = selectedScreen,
                onScreenSelected = {
                    selectedScreen = it
                }
            )
        }
    ) { innerPadding ->
        when (selectedScreen) {
            0 -> EventQueueScreen(
                modifier = Modifier.padding(innerPadding)
            )
            1 -> StatisticsScreen(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}