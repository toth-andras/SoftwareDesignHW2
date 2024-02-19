package org.example.commands.factories

import org.example.Application
import org.example.commands.Command
import org.example.commands.navigation.BackCommand
import org.example.commands.orders.CreateOrderCommand
import org.example.commands.orders.ShowOrdersCommand

/**
 * Определяет список команд, доступных в состоянии работы с заказами посетителю.
 */
class OrdersStateVisitorCommandFactory: CommandFactory<Application> {
    override fun createCommandSet(): Iterable<Command<Application>> {
        return listOf(ShowOrdersCommand(), CreateOrderCommand(), BackCommand())
    }
}