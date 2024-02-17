package org.example.states.auth

import org.example.Application
import org.example.states.ApplicationState
import org.example.utils.ConsoleOutputHelper
import org.example.utils.OutputMessageType

/**
 * Состояние выхода из пользовательского аккаунта.
 */
class LogoutState(application: Application, previousState: ApplicationState): ApplicationState(application, previousState) {
    override fun process() {
        println("Выйти из учётной записи (Y/N): ")
        application.state = previousState

        if (readln() == "Y") {
            application.session.deAuthorizeUser()
            ConsoleOutputHelper.printMessage("Выполнен выход из учётной записи", OutputMessageType.Info)
        }
    }
}