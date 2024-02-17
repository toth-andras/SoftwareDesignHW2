package org.example

import org.example.states.ApplicationState
import org.example.crud.auth.UserStorage
import org.example.crud.auth.UserStorageJson
import org.example.entities.auth.Session
import org.example.utils.AuthOutputHelper
import org.example.utils.ConsoleOutputHelper

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