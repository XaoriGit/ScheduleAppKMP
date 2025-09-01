package ru.xaori.schedule.features.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.xaori.schedule.common.AppError
import ru.xaori.schedule.common.ResultWrapper
import ru.xaori.schedule.features.schedule.data.ScheduleRepository
import ru.xaori.schedule.features.schedule.model.ScheduleUiState

class ScheduleViewModel(
    private val scheduleRepository: ScheduleRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<ScheduleUiState>(ScheduleUiState.Loading)
    val uiState: StateFlow<ScheduleUiState> = _uiState

    init {
        getSchedule()
    }

    fun getSchedule() {
        _uiState.value = ScheduleUiState.Loading
        viewModelScope.launch {
            when (val scheduleData = scheduleRepository.getSchedule()) {
                is ResultWrapper.Success -> {
                    _uiState.value = ScheduleUiState.Success(scheduleData.value)
                }

                is ResultWrapper.Error -> {
                    val error = scheduleData.error
                    _uiState.value = ScheduleUiState.Error(
                        when (error) {
                            is AppError.NoInternet -> "Ошибка соединения"
                            is AppError.HttpError -> "Ошибка сервера (${error.code})"
                            is AppError.Unknown -> "Неизвестная ошибка: ${error.throwable.message}"
                        }
                    )
                }
            }
        }
    }
}