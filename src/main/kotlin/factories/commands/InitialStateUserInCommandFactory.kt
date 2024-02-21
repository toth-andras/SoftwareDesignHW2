package org.example.factories.commands

import org.example.Application
import org.example.commands.Command
import org.example.commands.auth.DeleteAccountCommand
import org.example.commands.auth.ExitCommand
import org.example.commands.auth.LogoutCommand
import org.example.commands.navigation.MainMenuCommand

/**
 * Создает команды, доступные в начальном состоянии приложения при условии, что пользователь авторизован.
 */
class InitialStateUserInCommandFactory: CommandFactory<Application> {
    override fun createCommandSet(): Iterable<Command<Application>> {
        return listOf(MainMenuCommand(), LogoutCommand(), DeleteAccountCommand(), ExitCommand())
    }
}