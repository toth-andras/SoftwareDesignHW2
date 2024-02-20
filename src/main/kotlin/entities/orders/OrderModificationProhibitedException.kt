package org.example.entities.orders

/**
 * Вызывается при попытке добавить блюдо в заказ, когда он уже готов, отменён или оплачен.
 */
class OrderModificationProhibitedException(orderStatus: OrderStatus):
    Exception("Нельзя добавить блюдо в заказ со статусом, отличным от ${OrderStatus.UnderModification}." +
            " Текущий статус заказа: $orderStatus") {}