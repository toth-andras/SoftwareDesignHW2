package org.example.utils.orderRepresentation

import org.example.entities.orders.Order

/**
 * Представляет обобщённый интерфейс объекта, инкапсулирующего
 * одну из стратегий отображения заказов пользователю.
 */
interface OrderPresentationStrategy {
    /**
     * Возвращает представление заказа в виде строки.
     * @param order заказ.
     * @return представление заказа в виде строки, созданное по определенной стратегии.
     */
    fun presentOrder(order: Order): String
}