package ru.xaori.schedule.features.schedule.data

import ru.xaori.schedule.features.schedule.model.ScheduleDataResponse
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class ScheduleRepository(private val scheduleApi: ScheduleApi) {
    @OptIn(ExperimentalTime::class)
    suspend fun getSchedule(clientName: String): Result<ScheduleDataResponse> {
        return try {
//            val res = scheduleApi.getSchedule(clientName, Clock.System.now())
            val res = scheduleApi.getSchedule(clientName, Instant.parse("2025-05-19T00:00:00Z"))
            Result.success(res)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}