package ru.xaori.schedule.features.clientChoice

import com.russhwolf.settings.ExperimentalSettingsApi
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.xaori.schedule.features.clientChoice.data.ClientChoiceApi
import ru.xaori.schedule.features.clientChoice.data.ClientChoiceRepository
import ru.xaori.schedule.features.clientChoice.data.ClientChoiceStorage

@OptIn(ExperimentalSettingsApi::class)
val ClientChoiceModule = module {
    singleOf(::ClientChoiceApi)
    singleOf(::ClientChoiceStorage)
    singleOf(::ClientChoiceRepository)

    viewModelOf(::ClientChoiceViewModel)
}