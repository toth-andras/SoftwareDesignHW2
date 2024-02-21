package org.example.factories.commands

import org.example.Application
import org.example.commands.Command
import org.example.commands.navigation.ManageMenuCommand
import org.example.commands.navigation.MenuStatisticsCommand
import org.example.commands.navigation.BackCommand
import org.example.commands.navigation.OrdersCommand

/**
 * Возвращет список команд, доступных в состоянии главного меню администратору.
 */
class MainMenuStateAdminCommandFactory: CommandFactory<Application> {
    override fun createCommandSet(): Iterable<Command<Application>> {
        return listOf(ManageMenuCommand(), OrdersCommand(), MenuStatisticsCommand(), BackCommand())
    }
}