package entities.cooking

import org.example.entities.orders.Order
import org.example.entities.orders.OrderStatus
import org.example.observation.Event

/**
 * Представляет обертку над заказом, в виде которой заказ поступает в очередь на обработку.
 */
class CookingTask (val order: Order) {
    /**
     * Событие, которое будет вызвано по завершении приготовления заказа.
     */
    val orderFinished: Event<Order> = Event<Order>()

    /**
     * Отработка завершения приготовления заказа.
     */
    fun finishTask() {
        order.status = OrderStatus.Ready
        orderFinished(order)
    }
}