package ru.xaori.schedule.network

import io.ktor.client.HttpClient

expect fun createHttpClient(): HttpClient
