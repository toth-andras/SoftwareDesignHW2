package org.example.commands.auth

import org.example.Application
import org.example.commands.Command
import org.example.states.InitialState
import org.example.utils.ConsoleOutputHelper
import org.example.utils.OutputMessageType

/**
 * Команда, переводящая приложение в состояние выхода из учётной записи.
 */
class LogoutCommand(override var description: String = "Выйти из учётной записи.") : Command<Application> {
    override fun execute(argument: Application) {
        print("Выйти из учётной записи (Y/N): ")
        if (readln() == "Y") {
            argument.session.deAuthorizeUser()
            ConsoleOutputHelper.printMessage("Выполнен выход из учётной записи", OutputMessageType.Info)
            argument.state = InitialState(argument)
        }
    }
}