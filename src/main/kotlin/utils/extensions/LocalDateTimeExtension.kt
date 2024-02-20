package org.example.utils.extensions

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * Расширяет функционал структуры данных LocalDateTimeExtension.
 */
class LocalDateTimeExtension {
    companion object {
        /**
         * Выводит данные в привычном пользователю формате.
         */
        fun LocalDateTime.toBeautifulString(): String {
            return "${hour}:$minute ${dayOfMonth}.${monthNumber}.$year"
        }

        /**
         * Упрощает получение текущей даты.
         */
        fun LocalDateTime.Companion.now(): LocalDateTime {
            return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        }
    }
}