package org.example.commands.factories

import org.example.Application
import org.example.commands.Command
import org.example.commands.menu.ManageMenuCommand
import org.example.commands.menu.MenuStatisticsCommand
import org.example.commands.navigation.BackCommand

/**
 * Возвращет список команд, доступных в состоянии главного меню администратору.
 */
class MainMenuStateAdminCommandFactory: CommandFactory<Application> {
    override fun createCommandSet(): Iterable<Command<Application>> {
        return listOf(ManageMenuCommand(), MenuStatisticsCommand(), BackCommand())
    }
}