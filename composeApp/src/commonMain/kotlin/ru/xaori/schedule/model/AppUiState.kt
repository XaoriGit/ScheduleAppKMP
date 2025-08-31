package ru.xaori.schedule.model

sealed class AppUiState {
    object Loading: AppUiState()
    object Success: AppUiState()
    object NewUser: AppUiState()
}