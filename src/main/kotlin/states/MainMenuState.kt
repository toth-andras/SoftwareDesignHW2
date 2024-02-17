package org.example.states

import org.example.Application
import org.example.commands.factories.CommandFactory
import org.example.commands.factories.MainMenuStateAdminCommandFactory
import org.example.commands.factories.MainMenuStateVisitorCommandFactory
import org.example.utils.AuthChecker
import org.example.utils.CommandReader

/**
 * Состояние приложения, при котором отображается главное меню.
 */
class MainMenuState(application: Application, previousState: ApplicationState?): ApplicationState(application, previousState) {
    override fun process() {
        AuthChecker.requireLogin(application)

        val factory: CommandFactory<Application> =
            if (application.session.user!!.isAdmin) MainMenuStateAdminCommandFactory()
            else MainMenuStateVisitorCommandFactory()
        val commandReader = CommandReader(factory)

        commandReader.displayCommands()
        commandReader.readAndExecute(application)
    }
}