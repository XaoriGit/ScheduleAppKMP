package ru.xaori.schedule.features.clientChoice.model


data class ClientChoiceUiState(
    val searchQuery: String = "",
    val selectedTabIndex: Int = 0,
    val dataState: ClientChoiceDataState = ClientChoiceDataState.Loading
)

sealed class ClientChoiceDataState {
    object Loading: ClientChoiceDataState()
    data class Success(
        val clientChoice: ClientChoiceResponse,
        val searchQuery: String = "",
        val selectedTab: Int = ClientTypeDestination.Group.ordinal
    ): ClientChoiceDataState()
    data class Error(val detail: String): ClientChoiceDataState()
}