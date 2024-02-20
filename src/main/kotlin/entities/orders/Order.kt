package org.example.entities.orders

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.example.entities.auth.User
import org.example.entities.menu.MenuItem
import org.example.utils.extensions.LocalDateTimeExtension.Companion.now

/**
 * Представляет один заказ посетителя.
 * @param id идентификатор заказа.
 * @param userId идентификатор посетителя, которому принадлежит заказ.
 * @param menuItems идентификатры всех блюд, входящих в заказ.
 * @param status статус заказа.
 * @param priority приоритет заказа.
 * @param totalPrice суммарная стоимость всех блюд в заказе.
 * @param timeToCook время, необходимое на приготовление заказа.
 * @param date дата и время создания заказа.
 *
 * В заказе хранятся не id, а непосредственно объекты блюд, чтобы
 * гарантировать, что заказ после оформления будет выполнен, даже
 * если какое-то из блюд будет удалено.
 */
@Serializable
class Order private constructor(var id: Int,
                                val userId: Int,
                                var menuItems: MutableList<MenuItem>,
                                var status: OrderStatus,
                                var priority: Int,
                                var totalPrice: Int,
                                var timeToCook: Int,
                                val date: LocalDateTime) {
    /**
     * Создает новый заказ.
     * @param id идентификатор заказа.
     * @param user посетитель, оформивший заказ.
     * @param menuItems коллекция всех блюд, включенных в заказ посетителем.
     * @param date дата и время создания заказа.
     */
    constructor(id: Int,
                user: User,
                menuItems: Iterable<MenuItem>,
                priority: Int = 0,
                date: LocalDateTime = LocalDateTime.now())
            : this (id, user.id, menuItems.toMutableList(), OrderStatus.Created, priority, menuItems.sumOf { it.price }, menuItems.sumOf { it.timeToCook }, date)

    /**
     * Добавляет в заказ блюдо.
     * @param menuItem блюдо, которое необходимо добавить.
     */
    fun addMenuItem(menuItem: MenuItem) {
        if (status != OrderStatus.Created && status != OrderStatus.OnCook) {
            throw OrderModificationProhibitedException(status)
        }

        menuItems.addLast(menuItem)
        totalPrice += menuItem.price
        timeToCook += menuItem.timeToCook
    }
}