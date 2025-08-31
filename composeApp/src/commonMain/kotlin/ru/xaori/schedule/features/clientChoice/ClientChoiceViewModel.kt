package ru.xaori.schedule.features.clientChoice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.xaori.schedule.features.clientChoice.data.ClientChoiceRepository
import ru.xaori.schedule.features.clientChoice.model.ClientChoiceDataState
import ru.xaori.schedule.features.clientChoice.model.ClientChoiceUiState

class ClientChoiceViewModel(private val clientChoiceRepository: ClientChoiceRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(ClientChoiceUiState())
    val uiState: StateFlow<ClientChoiceUiState> = _uiState

    init {
        getClients()
    }

    private fun getClients() {
        _uiState.update { it.copy(dataState = ClientChoiceDataState.Loading) }

        viewModelScope.launch {
            val clients = clientChoiceRepository.getClients()
            _uiState.update {
                if (clients.isSuccess) {
                    val data = clients.getOrThrow()
                    it.copy(dataState = ClientChoiceDataState.Success(data))
                } else {
                    it.copy(
                        dataState = ClientChoiceDataState.Error(
                            clients.exceptionOrNull()?.message ?: "Unknown error"
                        )
                    )
                }
            }
        }
    }

    fun onSearchQueryChange(newQuery: String) {
        _uiState.update { it.copy(searchQuery = newQuery) }
    }

    fun onTabSelected(index: Int) {
        _uiState.update { it.copy(selectedTabIndex = index) }
    }

    fun onClickClientChoice(value: String, onComplete: () -> Unit) {
        viewModelScope.launch {
            clientChoiceRepository.setClient(value)
            onComplete()
        }
    }
}