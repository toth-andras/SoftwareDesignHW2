package org.example.entities.statistics

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlin.math.max

/**
 * Представляет класс, хранящий информацию об одном блюде.
 * @param id идентификатор объекта.
 * @param menuItemId идентификатор блюда, для которого сохраняется статистика.
 * @param reviews отзывы на блюдо.
 * @param _timesPurchased сколько порций блюда было заказано.
 * @param _profit прибыль от продажи блюда.
 * @param _lastPurchaseDate дата и время последней продажи блюда.
 */
@Serializable
    class MenuItemStatistics private constructor(var id: Int,
                                                 val menuItemId: Int,
                                                 val reviews: MutableList<Review>,
                                                 private var _timesPurchased: Int,
                                                 private var _profit: Int,
                                                 private var _lastPurchaseDate: LocalDateTime?) {

    /**
     * Создает объект, хпанящий статистику.
     * @param id идентификатор объекта.
     * @param menuItemId идентификатор блюда, для которого сохраняется статистика.
     */
    constructor(id: Int, menuItemId: Int): this(id, menuItemId, mutableListOf(),0, 0, null)

    /**
     * Сколько порций блюда было заказано.
     */
    var timesPurchased: Int
        get() = _timesPurchased
        set(value) {
            _timesPurchased = max(0, value)
        }

    /**
     * Прибыль от продажи блюда.
     */
    var profit: Int
        get() = _profit
        set(value) {
            _profit = max(0, value)
        }

    /**
     * Дата и время последнего заказа данного блюда.
     */
    var lastPurchaseDate: LocalDateTime?
        get() = _lastPurchaseDate
        set(value) {
            if (lastPurchaseDate == null) {
                _lastPurchaseDate = value
            }
            else {
                if (value == null) return

                if (_lastPurchaseDate!! < value) {
                    _lastPurchaseDate = value
                }
            }
        }
}