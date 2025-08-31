package ru.xaori.schedule

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.SuspendSettings
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.xaori.schedule.features.clientChoice.ClientChoiceModule
import ru.xaori.schedule.features.schedule.ScheduleModule
import ru.xaori.schedule.network.createHttpClient
import ru.xaori.schedule.settings.provideSettings

@OptIn(ExperimentalSettingsApi::class)
val CommonModule = module {
    single<SuspendSettings> { provideSettings() }
    single { createHttpClient() }

    viewModelOf(::AppViewModel)

    includes(ScheduleModule)
    includes(ClientChoiceModule)
}