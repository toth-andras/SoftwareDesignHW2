package org.example.crud.orders

import org.example.crud.Storage
import org.example.entities.auth.User
import org.example.entities.menu.MenuItem
import org.example.entities.orders.Order

/**
 * Представляет обобщенный интерфейс хранилища заказов.
 */
interface OrderStorage: Storage {
    /**
     * Возвращает все заказы, которые есть в хранилище.
     * @return коллекцию заказов.
     */
    fun getOrders(): Iterable<Order>

    /**
     * Возвращает все заказы пользователя с переданным идентификатором.
     * @param userId идентификатор пользователя.
     */
    fun getUserOrders(userId: Int): Iterable<Order>

    /**
     * Возвращает заказ с переданным идентификатором.
     * @param id идентификатор заказа.
     * @return заказ с переданным идентифкатором или null,
     * если заказа с таким идентификатором нет в хранилище.
     */
    fun getOrder(id: Int): Order?

    /**
     * Создает новый заказ и добавляет его в хранилище.
     * @param user посетитель, оформивший заказ.
     * @param menuItems блюда, которые заказал посетитель.
     * @return созданный заказ.
     */
    fun createOrder(user: User, menuItems: List<MenuItem>): Order

    /**
     * Добавляет заказ в хранилище.
     * @param order заказ.
     */
    fun addOrder(order: Order)

    /**
     * Удаляет заказ с переданным идентификатором из хранилища.
     * @param id идентификатор заказа.
     * @return true, если заказ с переданным идентификатором был удален и false иначе.
     */
    fun removeOrder(id: Int): Boolean
}