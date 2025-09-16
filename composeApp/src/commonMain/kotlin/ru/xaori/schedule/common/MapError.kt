package ru.xaori.schedule.common

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException

expect fun isNoInternetError(e: Throwable): Boolean

fun mapError(e: Throwable): AppError {
    return when {
        isNoInternetError(e) -> AppError.NoInternet

        e is ClientRequestException -> {
            if (e.response.status.value == 404) {
                AppError.NotFound
            } else {
                AppError.HttpError(e.response.status.value, e.message)
            }
        }

        e is RedirectResponseException -> AppError.HttpError(e.response.status.value, e.message)
        e is ClientRequestException -> AppError.HttpError(e.response.status.value, e.message)
        e is ServerResponseException -> AppError.HttpError(e.response.status.value, e.message)

        else -> AppError.Unknown(e)
    }
}