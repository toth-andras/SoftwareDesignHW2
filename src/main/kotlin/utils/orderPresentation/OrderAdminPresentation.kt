package org.example.utils.orderPresentation

import org.example.crud.auth.UserStorage
import org.example.entities.orders.Order
import org.example.utils.extensions.LocalDateTimeExtension.Companion.toBeautifulString
import org.example.utils.extensions.OrderStatusExtension.Companion.toBeautifulString
import org.example.utils.StringColorizer
import org.example.utils.orderRepresentation.OrderPresentationStrategy

/**
 * Инкапсулирует стратегию создания строчного отображения заказа для администратора.
 */
class OrderAdminPresentation(private var userStorage: UserStorage): OrderPresentationStrategy {
    override fun presentOrder(order: Order): String {
        val header = "— ${StringColorizer.toYellow("${order.id}")} —\n" + "Заказ от пользователя ${userStorage.getUser(order.userId)?.login ?: ""} " +
                "(${order.date.toBeautifulString()}): \n"
        val ordersBody = "[${order.menuItems.map{it.name}.joinToString(", "){ it }}]"
        val ending = "\nСтоимость заказа: ${order.totalPrice}, время приготовления: ${order.timeToCook}" +
                     "\nСтатус: ${order.status.toBeautifulString()}\n"

        return header + ordersBody + ending
    }
}