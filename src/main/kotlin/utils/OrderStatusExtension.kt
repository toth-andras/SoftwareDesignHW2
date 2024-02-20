package org.example.utils

import org.example.entities.orders.OrderStatus

class OrderStatusExtension {
    companion object {
        fun OrderStatus.toBeautifulString(): String{
            return when (this) {
                OrderStatus.Created -> "В очереди"
                OrderStatus.OnCook -> StringColorizer.toBlue("Готовится")
                OrderStatus.Ready -> StringColorizer.toGreen("Готов")
                OrderStatus.Cancelled -> StringColorizer.toRed("Отменен")
                OrderStatus.Paid -> StringColorizer.toPurple("Оплачен")
            }
        }
    }
}