package ru.xaori.schedule.features.schedule

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.xaori.schedule.features.schedule.data.ScheduleApi
import ru.xaori.schedule.features.schedule.data.ScheduleRepository

val ScheduleModule = module {
    singleOf(::ScheduleApi)
    singleOf(::ScheduleRepository)

    viewModelOf(::ScheduleViewModel)
}