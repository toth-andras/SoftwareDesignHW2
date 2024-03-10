package org.example.commands.orders

import org.example.Application
import org.example.commands.Command
import org.example.utils.ioHelpers.ConsoleInputHelper
import org.example.utils.ioHelpers.ConsoleOutputHelper
import org.example.utils.orderPresentation.OrderAdminPresentation
import org.example.utils.orderPresentation.OrderVisitorPresentation

/**
 * Команда, отображающая на экран заказы.
 */
class ShowOrdersCommand(override var description: String = "Просмотреть заказы"): Command<Application> {
    override fun execute(argument: Application) {
        val isAdmin = argument.session.user!!.isAdmin

        val presenter = if (isAdmin) OrderAdminPresentation(argument.userStorage) else OrderVisitorPresentation()
        val orders =
            if (isAdmin) argument.orderStorage.getOrders()
            else argument.orderStorage.getUserOrders(argument.session.user!!.id)
        ConsoleOutputHelper.displayOrders(orders, presenter, isAdmin)
        println()
        ConsoleInputHelper.readEnterPress()
    }
}