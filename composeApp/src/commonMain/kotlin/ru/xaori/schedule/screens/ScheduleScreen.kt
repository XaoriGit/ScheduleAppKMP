package ru.xaori.schedule.screens

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import ru.xaori.schedule.features.schedule.ScheduleViewModel
import ru.xaori.schedule.features.schedule.model.AppBarStatus
import ru.xaori.schedule.features.schedule.model.ScheduleUiState
import ru.xaori.schedule.features.schedule.ui.LastUpdatedDate
import ru.xaori.schedule.features.schedule.ui.AnimatedAppBar
import ru.xaori.schedule.features.schedule.ui.ScheduleList
import ru.xaori.schedule.features.schedule.ui.WeekDaysRow
import schedule.composeapp.generated.resources.Res
import schedule.composeapp.generated.resources.ic_refresh
import schedule.composeapp.generated.resources.ic_settings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    goToSettings: () -> Unit,
    viewModel: ScheduleViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }
    val stateRefresh = rememberPullToRefreshState()


    LaunchedEffect(uiState) {
        if (uiState !is ScheduleUiState.Loading && isRefreshing) {
            isRefreshing = false
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp, 8.dp),
    ) {
        AnimatedAppBar("Расписание", when(val state = uiState) {
            is ScheduleUiState.Loading -> AppBarStatus.Loading
            is ScheduleUiState.Success -> AppBarStatus.SubTitle(
                "для ${state.scheduleData.clientName}"
            )
            is ScheduleUiState.Error -> AppBarStatus.SubTitleError(state.detail)
        }) {
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

        when (val state = uiState) {
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
                    pageCount = { state.scheduleData.schedules.size }
                )

                WeekDaysRow(pagerState.currentPage, state.scheduleData.schedules) { value ->
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(value, animationSpec = tween())
                    }
                }
                LastUpdatedDate(state.scheduleData.lastUpdate)
                PullToRefreshBox(
                    state = stateRefresh,
                    isRefreshing = isRefreshing,
                    onRefresh = {
                        isRefreshing = true
                        viewModel.getSchedule()
                    },
                    indicator = {
                        Indicator(
                            modifier = Modifier.align(Alignment.TopCenter),
                            isRefreshing = isRefreshing,
                            containerColor = MaterialTheme.colorScheme.surface,
                            color = MaterialTheme.colorScheme.primary,
                            state = stateRefresh
                        )
                    },
                ) {
                    ScheduleList(state.scheduleData.schedules, pagerState)
                }
            }

            is ScheduleUiState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Что-то пошло не так",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            "Проверьте интернет и попробуйте снова",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Button(
                        onClick = { viewModel.getSchedule() },
                        contentPadding = PaddingValues(16.dp, 12.dp),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Icon(
                            painterResource(Res.drawable.ic_refresh),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(20.dp),

                            )
                        Text(
                            "Попробовать снова",
                            color = MaterialTheme.colorScheme.onSecondary,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }
}