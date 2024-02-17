package org.example.commands.auth

import org.example.Application
import org.example.states.auth.RegisterState
import org.example.commands.Command

/**
 * Команда, переводящая приложение в состояние регистрации пользователя.
 */
class RegisterCommand(override var description: String = "Регистрация") : Command<Application> {
    override fun execute(argument: Application) {
        argument.state = RegisterState(argument, argument.state)
    }
}