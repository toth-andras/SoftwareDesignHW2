package org.example.utils.orderPresentation

import org.example.entities.orders.Order
import org.example.utils.LocalDateTimeExtension.Companion.toBeautifulString
import org.example.utils.OrderStatusExtension.Companion.toBeautifulString
import org.example.utils.orderRepresentation.OrderPresentationStrategy

/**
 * Создает укороченное строковое представление заказа для пользователя.
 */
class OrderVisitorShortPresentation: OrderPresentationStrategy {
    override fun presentOrder(order: Order): String {
        return "${order.id}. Блюда: ${order.menuItems.joinToString(", "){it.name}}." +
                "Статус: ${order.status.toBeautifulString()}. Дата заказа: ${order.date.toBeautifulString()}"
    }
}