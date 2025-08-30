package ru.xaori.schedule.features.schedule.model

sealed class ScheduleUiState {
    object Loading: ScheduleUiState()
    data class Success(val scheduleData: ScheduleDataResponse): ScheduleUiState()
    data class Error(val detail: String): ScheduleUiState()
}