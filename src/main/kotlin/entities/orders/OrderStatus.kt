package org.example.entities.orders

/**
 * Представляет статусы заказа.
 */
enum class OrderStatus {
    Created,
    OnCook,
    Ready,
    Cancelled,
    Paid
}