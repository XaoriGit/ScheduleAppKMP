package ru.xaori.schedule.common

actual fun isNoInternetError(e: Throwable): Boolean =
    e.message?.contains("Failed to fetch") == true