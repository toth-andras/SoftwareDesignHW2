package org.example.utils.orderPresentation

import org.example.crud.auth.UserStorage
import org.example.entities.orders.Order
import org.example.utils.LocalDateTimeExtension.Companion.toBeautifulString
import org.example.utils.orderRepresentation.OrderPresentationStrategy

/**
 * Инкапсулирует стратегию создания строчного отображения заказа для администратора.
 */
class OrderAdminPresentation(private var userStorage: UserStorage): OrderPresentationStrategy {
    override fun presentOrder(order: Order): String {
        return "— ${order.id} —\n" + "Заказ от пользователя ${userStorage.getUser(order.userId)?.login ?: ""} (${order.date.toBeautifulString()}): \n" +
                "[${order.menuItems.map{it.name}.joinToString(", "){ it }}]" +
                "\nСтоимость заказа: ${order.totalPrice}, время приготовления: ${order.timeToCook}" +
                "\nСтатус: ${order.status}\n"
    }
}