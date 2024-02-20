package org.example.utils.orderPresentation

import org.example.entities.orders.Order
import org.example.utils.LocalDateTimeExtension.Companion.toBeautifulString
import org.example.utils.OrderStatusExtension.Companion.toBeautifulString
import org.example.utils.orderRepresentation.OrderPresentationStrategy

/**
 * Инкапсулирует стратегию создания строчного отображения заказа для посетителя.
 */
class OrderVisitorPresentation: OrderPresentationStrategy {
    override fun presentOrder(order: Order): String {
        val header = "— ${order.id} —\n" + "Заказ от ${order.date.toBeautifulString()}:\n"
        val ordersBody = order.menuItems
            .map{Pair(it.name, it.price)}
            .joinToString(separator = "\n-\t", prefix = "-\t") {it.first + " ........ " + it.second}
        val ending = "\nИтого: ${order.totalPrice} \uD83E\uDE99  \nСтатус: ${order.status.toBeautifulString()}\n"

        return header + ordersBody + ending
    }
}