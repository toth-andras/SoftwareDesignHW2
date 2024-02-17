package org.example.commands.navigation

import org.example.Application
import org.example.commands.Command

/**
 * Команда, переводящая в предыдущее состояние.
 */
class BackCommand(override var description: String = "Назад") : Command<Application> {
    override fun execute(argument: Application) {
        argument.state = argument.state?.previousState
    }
}