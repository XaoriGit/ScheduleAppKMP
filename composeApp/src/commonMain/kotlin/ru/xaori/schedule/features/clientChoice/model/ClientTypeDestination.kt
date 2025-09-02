package ru.xaori.schedule.features.clientChoice.model

import ru.xaori.schedule.common.Emoji

enum class ClientTypeDestination(val title: String) {
    Group("${Emoji.Group.unicode} Группа"),
    Teacher("${Emoji.Teacher.unicode} Преподаватель")
}