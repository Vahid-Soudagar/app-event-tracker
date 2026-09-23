package com.example.appeventtracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appeventtracker.sdk.model.QueueEvent
import com.example.appeventtracker.ui.theme.BorderColor
import com.example.appeventtracker.ui.theme.CartLight
import com.example.appeventtracker.ui.theme.InstallLight
import com.example.appeventtracker.ui.theme.ProcessingBlue
import com.example.appeventtracker.ui.theme.PurchaseLight
import com.example.appeventtracker.ui.theme.RetryOrange
import com.example.appeventtracker.ui.theme.SuccessGreen
import com.example.appeventtracker.ui.theme.TextSecondary
import com.example.appeventtracker.ui.theme.VisitLight
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val timeFormatter = SimpleDateFormat("hh:mm:ss a", Locale.getDefault())

@Composable
fun EventItem(
    event: QueueEvent,
    modifier: Modifier = Modifier
        .padding(bottom = 8.dp, start = 16.dp, end = 16.dp)
) {

    val eventColor = when (event.type) {
        "INSTALL" -> InstallLight
        "VISIT" -> VisitLight
        "ADD_TO_CART" -> CartLight
        "PURCHASE" -> PurchaseLight
        else -> BorderColor
    }

    // Tick every second while a retry is scheduled so the countdown stays live
    var now by remember { mutableLongStateOf(System.currentTimeMillis()) }
    LaunchedEffect(event.nextRetryAt) {
        val retryAt = event.nextRetryAt ?: return@LaunchedEffect
        now = System.currentTimeMillis()
        while (now < retryAt) {
            delay(1_000L)
            now = System.currentTimeMillis()
        }
    }

    val (statusText, statusColor) = when (event.status) {
        "PROCESSED" -> "Processed ✓" to SuccessGreen
        "PROCESSING" -> "Processing" to ProcessingBlue
        "FAILED" -> {
            val seconds = ((event.nextRetryAt ?: now) - now + 999) / 1_000
            (if (seconds > 0) "Retrying in ${seconds}s" else "Retrying") to RetryOrange
        }
        else -> "Queued" to TextSecondary
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = BorderColor,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(
                horizontal = 12.dp,
                vertical = 10.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Event color indicator
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(
                    color = eventColor,
                    shape = CircleShape
                )
        )

        // Event name + timestamp
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = event.type,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = timeFormatter.format(Date(event.timestamp)),
                modifier = Modifier.padding(top = 4.dp),
                fontSize = 9.sp,
                color = TextSecondary
            )
        }

        // Status
        Text(
            text = statusText,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = statusColor
        )
    }
}