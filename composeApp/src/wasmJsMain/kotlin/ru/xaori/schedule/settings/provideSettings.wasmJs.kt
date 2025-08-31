package ru.xaori.schedule.settings

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.StorageSettings
import com.russhwolf.settings.coroutines.SuspendSettings
import com.russhwolf.settings.coroutines.toSuspendSettings

@OptIn(ExperimentalSettingsApi::class)
actual fun provideSettings(): SuspendSettings {
    return StorageSettings().toSuspendSettings()
}