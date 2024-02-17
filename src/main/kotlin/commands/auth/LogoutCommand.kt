package org.example.commands.auth

import org.example.Application
import org.example.commands.Command

/**
 * Команда, переводящая приложение в состояние выхода из учётной записи.
 */
class LogoutCommand(override var description: String = "Выйти из учётной записи.") : Command<Application> {
    override fun execute(argument: Application) {
        argument.session.deAuthorizeUser()
    }
}