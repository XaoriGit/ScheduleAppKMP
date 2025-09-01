package ru.xaori.schedule.features.schedule.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScheduleEmptyListItem() {
    Card(
        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.surfaceVariant
        ), shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                "Расписания пока нет",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Card(
                colors = CardDefaults.cardColors(
                    MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    "Скоро добавим",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(10.dp, 6.dp)
                )
            }
        }
    }
}