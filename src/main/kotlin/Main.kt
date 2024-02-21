package org.example
import entities.cooking.CookingTask
import org.example.entities.orders.OrderStatus
import org.example.states.InitialState

fun main() {
    val application = Application()
    application.state = InitialState(application)
    application.initialize()

    while(!application.exitRequired) {
        application.process()
    }

    application.destruct()
}