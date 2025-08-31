package ru.xaori.schedule

import android.app.Application
import android.content.Context

class AppContext : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}
lateinit var appContext: Context