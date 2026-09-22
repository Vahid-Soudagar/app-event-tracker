package com.example.appeventtracker.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.appeventtracker.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.top_app_bar),
                fontWeight = FontWeight.Bold
            )
        }
    )

}