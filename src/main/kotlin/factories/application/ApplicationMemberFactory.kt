package org.example.factories.application

import org.example.crud.auth.UserStorage
import org.example.crud.menu.MenuItemStorage
import org.example.crud.orders.OrderStorage
import org.example.crud.statistics.MenuItemStatisticsStorage

/**
 * Представляет интерфейс для всех объектов, которые инициализируют члены класса Application.
 */
interface ApplicationMemberFactory {
    /**
     * Снабжает класс Application хранилищем пользователей.
     */
    fun getUserStorage(): UserStorage

    /**
     * Снабжает класс Application хранилищем блюд.
     */
    fun getMenuItemStorage(): MenuItemStorage

    /**
     * Снабжает класс Application хранилищем заказов.
     */
    fun getOrderStorage(): OrderStorage

    /**
     * Снабжает класс Application хранилищем статистик блюд.
     */
    fun getStatisticsStorage(): MenuItemStatisticsStorage

    /**
     * Задает для класса Application количество обработчиков заказов.
     */
    fun getCookingTaskWorkersCount(): Int

    /**
     * Задает для класса Application команду возврата к предыдущему состоянию.
     */
    fun getBackCommand(): String
}