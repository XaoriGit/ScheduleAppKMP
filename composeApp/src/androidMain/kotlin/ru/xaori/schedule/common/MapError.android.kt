package ru.xaori.schedule.common

import kotlinx.io.IOException

actual fun isNoInternetError(e: Throwable): Boolean =
    e is IOException || e is java.nio.channels.UnresolvedAddressException