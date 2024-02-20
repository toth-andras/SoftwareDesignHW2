package org.example.states.menu

import org.example.Application
import org.example.commands.factories.ManageMenuStateCommandFactory
import org.example.states.ApplicationState
import org.example.states.InitialState
import org.example.utils.AuthChecker
import org.example.utils.CommandReader
import org.example.utils.ioHelpers.ConsoleOutputHelper
import org.example.utils.menuRepresentation.MenuItemAdminPresentation

/**
 * Состояние приложения, в котором происходит управление меню.
 */
class ManageMenuState(application: Application, previousState: ApplicationState?): ApplicationState(application, previousState) {
    override fun process() {
        AuthChecker.requireAdmin(application, previousState ?: InitialState(application))

        ConsoleOutputHelper.displayMenu(application.menuStorage.getMenuItems(), MenuItemAdminPresentation())

        val commandReader = CommandReader(ManageMenuStateCommandFactory())
        println()
        commandReader.displayCommands()
        commandReader.readAndExecute(application)
    }
}