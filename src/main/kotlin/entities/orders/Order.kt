package org.example.entities.orders

import kotlinx.coroutines.sync.Mutex
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.example.entities.auth.User
import org.example.entities.menu.MenuItem
import org.example.utils.extensions.LocalDateTimeExtension.Companion.now
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

/**
 * Представляет один заказ посетителя.
 * @param id идентификатор заказа.
 * @param userId идентификатор посетителя, которому принадлежит заказ.
 * @param _menuItems все блюда, содержащиеся в заказе.
 * @param status статус заказа.
 * @param priority приоритет заказа.
 * @param _totalPrice суммарная стоимость всех блюд в заказе.
 * @param _timeToCook время, необходимое на приготовление заказа.
 * @param date дата и время создания заказа.
 * @param _lock необходим для корректной обработки добавления блюд в заказ во время его готовки.
 *
 * В заказе хранятся не id, а непосредственно объекты блюд, чтобы
 * гарантировать, что заказ после оформления будет выполнен, даже
 * если какое-то из блюд будет удалено.
 */
@Serializable
class Order private constructor(var id: Int,
                                val userId: Int,
                                private var _menuItems: MutableList<MenuItem>,
                                var status: OrderStatus,
                                var priority: Int,
                                private var _totalPrice: Int,
                                private var _timeToCook: Int,
                                val date: LocalDateTime,
                                private val _lock: Lock = ReentrantLock()) {
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
     * Суммарная стоимость всех блюд в заказе.
     */
    val totalPrice: Int
        get() = _totalPrice

    /**
     * Время преготовления заказа.
     */
    val timeToCook: Int
        get() = _timeToCook

    /**
     * Список блюд, входящих в заказ.
     */
    val menuItems: List<MenuItem>
        get() = _menuItems.toList()


    /**
     * Добавляет в заказ блюдо.
     * @param menuItem блюдо, которое необходимо добавить.
     * @param portions количество порций блюда, которое надо добавить в заказ.
     */
    fun addMenuItem(menuItem: MenuItem, portions: Int = 1) {
        require(portions > 0) {"Количество порций должно быть положительным"}
        menuItem.quantity -= portions

        if (status != OrderStatus.Created && status != OrderStatus.OnCook) {
            throw OrderModificationProhibitedException(status)
        }

        for (i in 1..portions) {
            _menuItems.add(menuItem)
        }

        _totalPrice += menuItem.price * portions
        _timeToCook += menuItem.timeToCook * portions
    }

    /**
     * Блокирует все потоки, работающие с объектом, кроме данного
     */
    fun requestOwnership() = _lock.lock()

    fun releaseOwnership() = _lock.unlock()
}