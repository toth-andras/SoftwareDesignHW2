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
        .forEach{application.cookingManager.addTask(CookingTask(it))}
    application.orderStorage.orderCreated += {application.cookingManager.addTask(CookingTask(it))}

    application.cookingManager.startWorking()
    while(!application.exitRequired) {
        application.process()
    }

    application.cookingManager.stopWorking()
    application.userStorage.destruct()
    application.menuStorage.destruct()
    application.orderStorage.destruct()
}