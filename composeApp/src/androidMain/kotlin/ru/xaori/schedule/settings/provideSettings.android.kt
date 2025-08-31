package ru.xaori.schedule.settings

import android.content.Context
import android.content.SharedPreferences
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.SharedPreferencesSettings
import com.russhwolf.settings.coroutines.SuspendSettings
import com.russhwolf.settings.coroutines.toSuspendSettings
import ru.xaori.schedule.appContext

@OptIn(ExperimentalSettingsApi::class)
actual fun provideSettings(): SuspendSettings {
    val delegate: SharedPreferences = appContext.getSharedPreferences("settings", Context.MODE_PRIVATE)
    return SharedPreferencesSettings(delegate).toSuspendSettings()
}