package org.example.states
import org.example.Application
import org.example.commands.factories.CommandFactory
import org.example.commands.factories.InitialStateNoUserCommandFactory
import org.example.commands.factories.InitialStateUserInCommandFactory
import org.example.utils.CommandReader

/**
 * Начальное состояние приложения.
 */
class InitialState(application: Application): ApplicationState(application) {
    override fun process() {
        val factory: CommandFactory<Application> =
            if (application.session.user != null) InitialStateUserInCommandFactory()
            else InitialStateNoUserCommandFactory()
        val commandReader = CommandReader(factory)

        commandReader.displayCommands()
        commandReader.readAndExecute(application)
    }
}