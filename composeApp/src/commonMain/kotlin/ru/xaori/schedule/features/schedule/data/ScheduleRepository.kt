package ru.xaori.schedule.features.schedule.data

import ru.xaori.schedule.common.ResultWrapper
import ru.xaori.schedule.common.mapError
import ru.xaori.schedule.features.clientChoice.data.ClientChoiceRepository
import ru.xaori.schedule.features.schedule.model.ScheduleDataResponse
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class ScheduleRepository(
    private val scheduleApi: ScheduleApi,
    private val clientChoiceRepository: ClientChoiceRepository
) {
    @OptIn(ExperimentalTime::class)
    suspend fun getSchedule(): ResultWrapper<ScheduleDataResponse> {
        return try {
            val clientName = clientChoiceRepository.getClient()
            val res = scheduleApi.getSchedule(clientName, Clock.System.now())
            ResultWrapper.Success(res)
        } catch (e: Exception) {
            ResultWrapper.Error(mapError(e))
        }
    }
}