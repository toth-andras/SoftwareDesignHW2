package org.example.commands.navigation

import org.example.Application
import org.example.commands.Command
import org.example.states.menu.ManageMenuState

class ManageMenuCommand(override var description: String = "Меню") : Command<Application> {
    override fun execute(argument: Application) {
        argument.state = ManageMenuState(argument, argument.state)
    }
}