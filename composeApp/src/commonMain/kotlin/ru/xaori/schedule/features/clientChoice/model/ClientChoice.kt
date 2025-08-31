package ru.xaori.schedule.features.clientChoice.model

import kotlinx.serialization.Serializable

@Serializable
data class ClientChoiceResponse(
    val groups: List<String>,
    val teachers: List<String>
)