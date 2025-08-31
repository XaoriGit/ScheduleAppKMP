package ru.xaori.schedule.features.clientChoice.data

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.SuspendSettings
import ru.xaori.schedule.features.clientChoice.data.ClientChoiceConstants.CLIENT

@OptIn(ExperimentalSettingsApi::class)
class ClientChoiceStorage(private val settings: SuspendSettings) {
    suspend fun setClient(client: String) = settings.putString(CLIENT, client)
    suspend fun getClient(): String? = settings.getStringOrNull(CLIENT)
}