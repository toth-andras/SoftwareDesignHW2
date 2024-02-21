package org.example.entities.statistics

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

/**
 * Представляет отзыв посетителя о блюде.
 * @param author имя посетителя, оставившего отзыв.
 * @param mark оценка посетителя.
 * @param message текстовый комментарий к отзыву.
 * @param date дата написания отзыва.
 */
@Serializable
class Review(val author: String, val mark: Int, val message: String, val date: LocalDateTime) {
    companion object {
        val minimalMark: Int = 1
        val maxMark: Int = 5
    }

    init {
        require(mark in 1..5) {"Оценка должна быть от $minimalMark до $maxMark"}
    }

    override fun toString(): String {
        return "$author ($mark/$maxMark): "
    }
}