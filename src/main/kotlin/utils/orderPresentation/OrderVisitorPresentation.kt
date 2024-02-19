package org.example.utils.orderPresentation

import org.example.entities.orders.Order
import org.example.utils.LocalDateTimeExtension.Companion.toBeautifulString
import org.example.utils.orderRepresentation.OrderPresentationStrategy

/**
 * Инкапсулирует стратегию создания строчного отображения заказа для посетителя.
 */
class OrderVisitorPresentation: OrderPresentationStrategy {
    override fun presentOrder(order: Order): String {
        return "Заказ от ${order.date.toBeautifulString()}: \n" +
                order.menuItems.joinToString(separator = "\n-\t", prefix = "-\t") { it.name  } +
                "\n----- Итого: ${order.totalPrice} \uD83E\uDE99 -----  \nСтатус: ${order.status}"
    }
}