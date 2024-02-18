package org.example.states

import org.example.Application
import org.example.utils.AuthChecker

/**
 * Состояние приложения, при котором происходит работа с заказами.
 */
class OrdersState(application: Application, previousState: ApplicationState?): ApplicationState(application, previousState) {
    override fun process() {
        AuthChecker.requireLogin(application)
        println("orders")
        readln()
    }
}