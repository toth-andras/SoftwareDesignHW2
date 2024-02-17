package org.example.utils

import kotlinx.datetime.LocalDateTime

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
    }
}