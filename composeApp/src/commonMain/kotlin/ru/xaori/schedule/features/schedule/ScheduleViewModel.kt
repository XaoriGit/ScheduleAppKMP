package ru.xaori.schedule.features.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.xaori.schedule.features.clientChoice.data.ClientChoiceRepository
import ru.xaori.schedule.features.schedule.data.ScheduleRepository
import ru.xaori.schedule.features.schedule.model.ScheduleUiState

class ScheduleViewModel(
    private val scheduleRepository: ScheduleRepository,
    private val clientChoiceRepository: ClientChoiceRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<ScheduleUiState>(ScheduleUiState.Loading)
    val uiState: StateFlow<ScheduleUiState> = _uiState

    init {
        getSchedule()
    }

    private fun getSchedule() {
        _uiState.value = ScheduleUiState.Loading
        viewModelScope.launch {
            val client = clientChoiceRepository.getClient()
            val scheduleData = scheduleRepository.getSchedule(client)
            if (scheduleData.isSuccess) {
                val data = scheduleData.getOrThrow()
                _uiState.value = ScheduleUiState.Success(data)
            } else {
                println(scheduleData.toString())
            }
        }
    }
}