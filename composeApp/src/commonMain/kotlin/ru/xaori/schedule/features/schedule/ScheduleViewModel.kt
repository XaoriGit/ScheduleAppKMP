package ru.xaori.schedule.features.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.xaori.schedule.common.AppError
import ru.xaori.schedule.common.ResultWrapper
import ru.xaori.schedule.features.schedule.data.ScheduleRepository
import ru.xaori.schedule.features.schedule.model.ScheduleEvents
import ru.xaori.schedule.features.schedule.model.ScheduleUiState

class ScheduleViewModel(
    private val scheduleRepository: ScheduleRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<ScheduleUiState>(ScheduleUiState.Loading)
    val uiState: StateFlow<ScheduleUiState> = _uiState

    private val _events = MutableSharedFlow<ScheduleEvents>()
    val events: SharedFlow<ScheduleEvents> = _events


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
                    when (val error = scheduleData.error) {
                        is AppError.NotFound -> {
                            _events.emit(ScheduleEvents.GoToStart)
                        }
                        is AppError.NoInternet -> {
                            _uiState.value = ScheduleUiState.Error("Ошибка соединения")
                        }
                        is AppError.HttpError -> {
                            _uiState.value = ScheduleUiState.Error("Ошибка сервера ${error.code}")
                        }
                        is AppError.Unknown -> {
                            _uiState.value = ScheduleUiState.Error("Неизвестная ошибка: ${error.throwable.message}")
                        }
                    }
                }
            }
        }
    }
}