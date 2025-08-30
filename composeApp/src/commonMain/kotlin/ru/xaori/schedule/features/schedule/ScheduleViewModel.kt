package ru.xaori.schedule.features.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.xaori.schedule.features.schedule.data.ScheduleRepository
import ru.xaori.schedule.features.schedule.model.ScheduleUiState

class ScheduleViewModel(private val scheduleRepository: ScheduleRepository): ViewModel() {
    private val _uiState = MutableStateFlow<ScheduleUiState>(ScheduleUiState.Loading)
    val uiState: StateFlow<ScheduleUiState> = _uiState

    init {
        getSchedule()
    }

    private fun getSchedule() {
        viewModelScope.launch {
            val scheduleData = scheduleRepository.getSchedule("ИСР-31")
            if (scheduleData.isSuccess) {
                val data = scheduleData.getOrThrow()
                _uiState.value = ScheduleUiState.Success(data)
            } else {
                println(scheduleData.toString())
            }
        }
    }
}