package ru.xaori.schedule.features.schedule.model

import kotlinx.serialization.Serializable

@Serializable
data class ScheduleClientsResponse(
    val groups: List<String>,
    val teachers: List<String>
)