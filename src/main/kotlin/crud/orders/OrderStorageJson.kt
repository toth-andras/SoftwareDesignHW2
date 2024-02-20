package org.example.crud.orders

import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.entities.auth.User
import org.example.entities.menu.MenuItem
import org.example.entities.orders.Order
import org.example.observation.Event
import java.io.File
import java.io.IOException
import java.util.Random

/**
 * Представляет хранилище блюд, хранящее данные в формате json.
 * @param sourcePath путь к файлу в формате .json, в котором хранятся данные о заказах.
 */
class OrderStorageJson(private val sourcePath: String): OrderStorage {
    private var _orders: MutableMap<Int, Order> = mutableMapOf()
    private var _nextId: Int = 0
    private val _random: Random = Random()

    override val orderCreated: Event<Order> = Event<Order>()

    override fun initialize() {
        _orders = try {
            Json.decodeFromString(File(sourcePath).readText())
        } catch (e: IOException) {
            mutableMapOf()
        }
        catch (e: SerializationException) {
            mutableMapOf()
        }

        if (_orders.any()) {
            _nextId = _orders.maxOf { it.value.id } + 1
        }
    }

    override fun destruct() {
        save()
    }

    override fun getOrders(): Iterable<Order> = _orders.values

    override fun getUserOrders(userId: Int): Iterable<Order> = _orders.values.filter { it.userId == userId }

    override fun getOrder(id: Int): Order? = _orders[id]

    override fun createOrder(user: User, menuItems: List<MenuItem>): Order {
        val order = Order(_nextId++, user, menuItems)
        order.priority = _random.nextInt(0, 10)
        _orders[order.id] = order
        save()
        orderCreated(order)
        return order
    }

    override fun removeOrder(id: Int): Boolean {
        if (!_orders.containsKey(id)) {
            return false
        }

        _orders.remove(id)
        save()
        return true
    }

    override fun addOrder(order: Order) {
        order.id = _nextId++
        _orders[order.id] = order
        save()
    }

    private fun save() {
        try {
            File(sourcePath).writeText(Json.encodeToString(_orders))
        }
        // В текущей реализации я решил, что объект просто будет игнорировать ошибку.
        catch (e: IOException) {
            return
        }
        catch (e: SerializationException) {
            return
        }
    }
}