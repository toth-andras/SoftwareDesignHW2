package org.example.entities.statistics

import kotlinx.datetime.LocalDateTime
import org.example.crud.statistics.MenuItemStatisticsStorage
import org.example.entities.menu.MenuItem
import org.example.entities.orders.Order
import org.example.utils.extensions.LocalDateTimeExtension.Companion.now

/**
 * Осуществляет контроль над сбором статистики по блюдам.
 */
class StatisticsManager(val statisticsStorage: MenuItemStatisticsStorage) {
    /**
     * Обработка добавления в меню нового блюда.
     */
    fun menuItemAdded(menuItem: MenuItem) {
        if (statisticsStorage.getStatistics(menuItem.id) != null) {
            return
        }

        statisticsStorage.createStatistics(menuItem)
    }

    /**
     * Обработка удаления блюда из меню.
     */
    fun menuItemRemoved(menuItem: MenuItem) = statisticsStorage.removeStatistics(menuItem.id)

    /**
     * Отработка создания нового заказа.
     */
    fun orderCreated(order: Order) {
        menuItemsOrdered(order.menuItems)
    }

    /**
     * Отработка обновления заказа.
     * @param update добавленные к заказу блюда.
     */
    fun orderUpdated(update: Iterable<MenuItem>) = menuItemsOrdered(update)

    /**
     * Отработка отмены заказа.
     */
    fun orderCancelled(order: Order) {
        order.menuItems.forEach {
            val statistics = statisticsStorage.getStatistics(it.id)
            if (statistics != null) {
                statistics.profit -= it.price
                statistics.timesPurchased -= 1
            }
        }
        statisticsStorage.save()
    }

    /**
     * Обработка добавления отзыва к блюду.
     * @param menuItem блюдо.
     * @author логин автора отзыва.
     * @param mark оценка блюда.
     * @param message комментарий отзыва.
     */
    fun reviewAdded(menuItem: MenuItem, author: String, mark: Int, message: String) {
        var statistics = statisticsStorage.getStatistics(menuItem.id)
        if (statistics == null) {
            statistics = statisticsStorage.createStatistics(menuItem)
        }

        statistics.reviews.add(Review(author, mark, message, LocalDateTime.now()))

        statisticsStorage.save()
    }

    private fun menuItemsOrdered(menuItems: Iterable<MenuItem>) {
        for (menuItem in menuItems) {
            var statistics = statisticsStorage.getStatistics(menuItem.id)
            if (statistics == null) {
                statistics = statisticsStorage.createStatistics(menuItem)
            }

            statistics.timesPurchased++
            statistics.profit += menuItem.price
            statistics.lastPurchaseDate = LocalDateTime.now()
        }
        statisticsStorage.save()
    }
}