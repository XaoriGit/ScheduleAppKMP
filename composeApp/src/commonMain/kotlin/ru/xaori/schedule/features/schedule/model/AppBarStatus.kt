package ru.xaori.schedule.features.schedule.model

sealed class AppBarStatus {
    data class SubTitle(val subTitle: String): AppBarStatus()
    data class SubTitleError(val subTitle: String): AppBarStatus()
    object Loading: AppBarStatus()
}