package org.example.factories.commands

import org.example.Application
import org.example.commands.Command
import org.example.commands.navigation.BackCommand
import org.example.commands.orders.CancelOrderCommand
import org.example.commands.orders.ShowOrdersCommand

/**
 * Определяет список команд, доступных в состоянии работы с заказами администратору.
 */
class OrdersStateAdminCommandFactory: CommandFactory<Application> {
    override fun createCommandSet(): Iterable<Command<Application>> {
        return listOf(ShowOrdersCommand(), CancelOrderCommand(), BackCommand())
    }
}