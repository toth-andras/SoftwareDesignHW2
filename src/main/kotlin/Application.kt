package org.example

import org.example.states.ApplicationState
import org.example.crud.auth.UserStorage
import org.example.crud.auth.UserStorageJson
import org.example.crud.menu.MenuItemStorage
import org.example.crud.menu.MenuItemStorageJson
import org.example.crud.orders.OrderStorage
import org.example.crud.orders.OrderStorageJson
import org.example.entities.auth.Session
import entities.cooking.CookingManager
import org.example.utils.ioHelpers.AuthOutputHelper
import org.example.utils.ioHelpers.ConsoleOutputHelper

/**
 * Представляет контектс приложения — здесь хранятся данные,
 * общие для разных его состояний.
 */
class Application () {
    /**
     * Хранит текущего пользователя и время его входа.
     */
    val session = Session()

    /**
     * Состояние приложения.
     */
    var state: ApplicationState? = null

    /**
     * Отображет, запросил ли пользователь выход из приложения.
     */
    var exitRequired: Boolean = false

    /**
     * Хранит команду перехода в предыдущее состояние.
     */
    var backCommand = "/back"

    /**
     * Хранилище со всеми пользователями приложения.
     */
    var userStorage: UserStorage = UserStorageJson("data/users.json")

    /**
     * Хранилища со всеми блюдами.
     */
    val menuStorage: MenuItemStorage = MenuItemStorageJson("data/menu.json")

    /**
     * Хранилище со всеми заказами.
     */
    val orderStorage: OrderStorage = OrderStorageJson("data/orders.json")

    /**
     * Кухня.
     */
    val kitchen: CookingManager = CookingManager(3)


    /**
     * Цикл работы приложения.
     */
    fun process() {
        ConsoleOutputHelper.clearConsole()
        AuthOutputHelper.printSessionUser(session)
        if (state == null) {
            exitRequired = true;
            return
        }

        state!!.process()
    }
}