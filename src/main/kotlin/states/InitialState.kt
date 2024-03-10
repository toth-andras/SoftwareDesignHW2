package org.example.states
import org.example.Application
import org.example.factories.commands.CommandFactory
import org.example.factories.commands.InitialStateNoUserCommandFactory
import org.example.factories.commands.InitialStateUserInCommandFactory
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