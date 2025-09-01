package ru.xaori.schedule.common

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.io.IOException

fun mapError(e: Throwable): AppError {
    return when (e) {
        is IOException,
        is TimeoutCancellationException -> AppError.NoInternet

        is RedirectResponseException -> AppError.HttpError(e.response.status.value, e.message)
        is ClientRequestException -> AppError.HttpError(e.response.status.value, e.message)
        is ServerResponseException -> AppError.HttpError(e.response.status.value, e.message)

        else -> AppError.Unknown(e)
    }
}