package org.example.commands.auth

import org.example.Application
import org.example.states.auth.LoginState
import org.example.commands.Command

/**
 * Команда, переводящая приложение в состояние атворизации пользователя.
 */
class LoginCommand(override var description: String = "Войти в аккаунт") : Command<Application> {
    override fun execute(argument: Application) {
        argument.state = LoginState(argument, argument.state)
    }
}