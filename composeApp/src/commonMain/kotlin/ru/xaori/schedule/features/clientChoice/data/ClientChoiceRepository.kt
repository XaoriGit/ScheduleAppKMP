package ru.xaori.schedule.features.clientChoice.data

import ru.xaori.schedule.common.ResultWrapper
import ru.xaori.schedule.common.mapError
import ru.xaori.schedule.features.clientChoice.model.ClientChoiceResponse

class ClientChoiceRepository(
    private val clientChoiceApi: ClientChoiceApi,
    private val clientChoiceStorage: ClientChoiceStorage
) {
    suspend fun getClients(): ResultWrapper<ClientChoiceResponse> {
        return try {
            val res = clientChoiceApi.getClients()
            ResultWrapper.Success(res)
        } catch (e: Throwable) {
            ResultWrapper.Error(mapError(e))
        }
    }

    suspend fun getClient(): String {
        return clientChoiceStorage.getClient() ?: "Клиент не найден"
    }

    suspend fun setClient(value: String) = clientChoiceStorage.setClient(value)
}