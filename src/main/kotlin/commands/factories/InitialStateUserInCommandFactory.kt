package org.example.commands.factories

import org.example.Application
import org.example.commands.Command
import org.example.commands.auth.DeleteAccountCommand
import org.example.commands.auth.ExitCommand
import org.example.commands.auth.LogoutCommand
import org.example.commands.auth.RegisterCommand

/**
 * Создает команды, доступные в начальном состоянии приложения при условии, что пользователь авторизован.
 */
class InitialStateUserInCommandFactory: CommandFactory<Application> {
    override fun createCommandSet(): Iterable<Command<Application>> {
        return listOf(LogoutCommand(), DeleteAccountCommand(), ExitCommand())
    }
}