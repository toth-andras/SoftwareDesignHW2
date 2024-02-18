package org.example.commands.navigation

import org.example.Application
import org.example.commands.Command
import org.example.states.OrdersState

/**
 * Команда, переводящая приложение в состояние работы с заказами.
 */
class OrdersCommand(override var description: String = "Заказы") : Command<Application> {
    override fun execute(argument: Application) {
        argument.state = OrdersState(argument, argument.state)
    }
}