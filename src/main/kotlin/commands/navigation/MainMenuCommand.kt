package org.example.commands.navigation

import org.example.Application
import org.example.commands.Command
import org.example.states.MainMenuState

/**
 * Переводит программу в состояние главного меню.
 */
class MainMenuCommand(override var description: String = "Главное меню") : Command<Application> {
    override fun execute(argument: Application) {
        argument.state = MainMenuState(argument, argument.state)
    }
}