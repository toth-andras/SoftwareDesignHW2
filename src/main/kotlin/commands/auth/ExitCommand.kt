package org.example.commands.auth

import org.example.Application
import org.example.commands.Command

/**
 * Команда, переводящая приложение в выключенное состояние.
 */
class ExitCommand(override var description: String = "Выйти из приложения") : Command<Application> {
    override fun execute(argument: Application) {
        argument.exitRequired = true
        argument.state = null
    }
}