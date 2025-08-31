package ru.xaori.schedule.features.clientChoice.data

import ru.xaori.schedule.features.clientChoice.model.ClientChoiceResponse

class ClientChoiceRepository(
    private val clientChoiceApi: ClientChoiceApi,
    private val clientChoiceStorage: ClientChoiceStorage
) {
    suspend fun getClients(): Result<ClientChoiceResponse> {
        return try {
            val res = clientChoiceApi.getClients()
            Result.success(res)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getClient(): String {
        return clientChoiceStorage.getClient() ?: "Клиент не найден"
    }
    suspend fun setClient(value: String) = clientChoiceStorage.setClient(value)
}