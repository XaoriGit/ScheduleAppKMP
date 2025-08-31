package ru.xaori.schedule.screens

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import ru.xaori.schedule.features.schedule.ScheduleViewModel
import ru.xaori.schedule.features.schedule.model.ScheduleUiState
import ru.xaori.schedule.features.schedule.ui.LastUpdatedDate
import ru.xaori.schedule.features.myAppBar.ui.MyAppBar
import ru.xaori.schedule.features.schedule.ui.ScheduleList
import ru.xaori.schedule.features.schedule.ui.WeekDaysRow
import schedule.composeapp.generated.resources.Res
import schedule.composeapp.generated.resources.ic_settings

@Composable
fun ScheduleScreen(
    goToSettings: () -> Unit,
    viewModel: ScheduleViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp, 8.dp),
    ) {
        MyAppBar("Расписание", "Тут пока ничего нет") {
            IconButton(
                onClick = goToSettings,
                modifier = Modifier.size(28.dp),
            ) {
                Icon(
                    painterResource(Res.drawable.ic_settings),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        when (val currentState = state) {
            is ScheduleUiState.Loading -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.width(36.dp),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surface,
                    )
                }
            }

            is ScheduleUiState.Success -> {
                val pagerState = rememberPagerState(
                    pageCount = { currentState.scheduleData.schedules.size }
                )

                WeekDaysRow(pagerState.currentPage, currentState.scheduleData.schedules) { value ->
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(value, animationSpec = tween())
                    }
                }
                LastUpdatedDate(currentState.scheduleData.lastUpdate)
                ScheduleList(currentState.scheduleData.schedules, pagerState)
            }

            is ScheduleUiState.Error -> {
                Text(currentState.detail)
            }
        }
    }
}