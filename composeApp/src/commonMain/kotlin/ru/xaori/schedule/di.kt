package ru.xaori.schedule

import org.koin.dsl.module
import ru.xaori.schedule.features.schedule.ScheduleModule
import ru.xaori.schedule.network.createHttpClient

val CommonModule = module {
    single { createHttpClient() }

    includes(ScheduleModule)
}