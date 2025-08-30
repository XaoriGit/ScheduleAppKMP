package ru.xaori.schedule.features.schedule.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ru.xaori.schedule.features.schedule.model.ScheduleClientsResponse
import ru.xaori.schedule.features.schedule.model.ScheduleDataResponse
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class ScheduleApi(private val client: HttpClient) {
    suspend fun getClients(): ScheduleClientsResponse {
        return client.get("schedule/clients") {
            contentType(ContentType.Application.Json)
        }.body()
    }

    @OptIn(ExperimentalTime::class)
    suspend fun getSchedule(clientName: String, clientTime: Instant): ScheduleDataResponse {
        return client.get("schedule") {
            contentType(ContentType.Application.Json)
            header("X-CLIENT-TIME", clientTime.toString())
            url {
                parameters.append("client_name", clientName)
            }
        }.body()
    }
}