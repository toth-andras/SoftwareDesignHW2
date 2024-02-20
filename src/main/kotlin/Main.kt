package org.example
import entities.cooking.CookingTask
import org.example.entities.orders.OrderStatus
import org.example.states.InitialState

fun main() {
    val application = Application()
    application.state = InitialState(application)
    application.userStorage.initialize()
    application.menuStorage.initialize()
    application.orderStorage.initialize()
    application.orderStorage.getOrders()
        .filter { it.status == OrderStatus.Created || it.status == OrderStatus.OnCook }
        .forEach{application.kitchen.addTask(CookingTask(it))}
    application.orderStorage.orderCreated += {application.kitchen.addTask(CookingTask(it))}

    application.kitchen.startWorking()
    while(!application.exitRequired) {
        application.process()
    }

    application.kitchen.stopWorking()
    application.userStorage.destruct()
    application.menuStorage.destruct()
    application.orderStorage.destruct()
}