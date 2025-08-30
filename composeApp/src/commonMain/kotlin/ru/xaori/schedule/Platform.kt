package ru.xaori.schedule

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform