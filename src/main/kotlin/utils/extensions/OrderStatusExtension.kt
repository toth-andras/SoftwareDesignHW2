package org.example.utils.extensions

import org.example.entities.orders.OrderStatus
import org.example.utils.StringColorizer

/**
 * Расширяет функционал класса OrderStatus.
 * @see OrderStatus
 */
class OrderStatusExtension {
    companion object {
        /**
         * Представляет статус заказа в удобном для пользователя виде.
         * @return строковое представление статуса заказа.
         */
        fun OrderStatus.toBeautifulString(): String{
            return when (this) {
                OrderStatus.Created -> "В очереди"
                OrderStatus.OnCook -> StringColorizer.toBlue("Готовится")
                OrderStatus.Ready -> StringColorizer.toGreen("Готов")
                OrderStatus.Cancelled -> StringColorizer.toRed("Отменен")
                OrderStatus.Paid -> StringColorizer.toPurple("Оплачен")
                OrderStatus.UnderModification -> "Изменяется"
                else -> ""
            }
        }
    }
}