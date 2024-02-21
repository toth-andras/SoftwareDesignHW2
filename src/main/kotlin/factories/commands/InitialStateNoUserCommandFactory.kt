package org.example.factories.commands

import org.example.Application
import org.example.commands.Command
import org.example.commands.auth.ExitCommand
import org.example.commands.auth.DeleteAccountCommand
import org.example.commands.auth.LoginCommand
import org.example.commands.auth.RegisterCommand

/**
 * Создает команды, доступные в начальном состоянии приложения при условии, что пользователь не авторизован.
 */
class InitialStateNoUserCommandFactory: CommandFactory<Application> {
    override fun createCommandSet(): Iterable<Command<Application>> {
        return listOf(LoginCommand(), RegisterCommand(), DeleteAccountCommand(), ExitCommand())
    }
}
