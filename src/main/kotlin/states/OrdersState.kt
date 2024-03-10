package org.example.states

import org.example.Application
import org.example.factories.commands.CommandFactory
import org.example.factories.commands.OrdersStateAdminCommandFactory
import org.example.factories.commands.OrdersStateVisitorCommandFactory
import org.example.utils.AuthChecker
import org.example.utils.CommandReader

/**
 * Состояние приложения, при котором происходит работа с заказами.
 */
class OrdersState(application: Application, previousState: ApplicationState?): ApplicationState(application, previousState) {
    override fun process() {
        AuthChecker.requireLogin(application)
        val isAdmin = application.session.user!!.isAdmin

        val factory: CommandFactory<Application> =
            if (isAdmin) OrdersStateAdminCommandFactory()
            else OrdersStateVisitorCommandFactory()


        val reader = CommandReader(factory)
        reader.displayCommands()
        reader.readAndExecute(application)
    }
}