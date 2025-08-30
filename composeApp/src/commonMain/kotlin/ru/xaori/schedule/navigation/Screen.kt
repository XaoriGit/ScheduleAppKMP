package ru.xaori.schedule.navigation

sealed class Screen(val route: String) {
    object Schedule: Screen("schedule")
    object ChangeClient: Screen("change_client")
}