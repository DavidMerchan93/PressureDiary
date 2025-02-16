package com.davidmerchan.pressurediary.presentation.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun formatTimestamp(timestamp: Long): String {
    val dateTime = Instant.fromEpochMilliseconds(timestamp)
        .toLocalDateTime(TimeZone.currentSystemDefault())

    return """
        ${dateTime.dayOfMonth}, ${dateTime.month} de ${dateTime.year}
        ${dateTime.hour} : ${dateTime.minute}
    """.trimIndent()
}