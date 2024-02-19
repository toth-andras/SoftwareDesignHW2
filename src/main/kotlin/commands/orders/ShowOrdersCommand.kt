package org.example.commands.orders

import org.example.Application
import org.example.commands.Command
import org.example.utils.ConsoleInputHelper
import org.example.utils.orderPresentation.OrderAdminPresentation
import org.example.utils.orderPresentation.OrderVisitorPresentation

/**
 * Команда, отображающая на экран заказы.
 */
class ShowOrdersCommand(override var description: String = "Просмотреть заказы"): Command<Application> {
    override fun execute(argument: Application) {
        val isAdmin = argument.session.user!!.isAdmin

        println("\n\n\n")
        println(if (isAdmin) "Заказы" else "Мои заказы")
        println("=========================================================================================")
        val presenter = if (isAdmin) OrderAdminPresentation(argument.userStorage) else OrderVisitorPresentation()
        var orders =
            if (isAdmin) argument.orderStorage.getOrders()
            else argument.orderStorage.getUserOrders(argument.session.user!!.id)
        orders = orders.sortedBy { it.date }.asReversed()
        orders.forEach{ println(presenter.presentOrder(it)); println() }

        ConsoleInputHelper.readEnterPress()
    }
}