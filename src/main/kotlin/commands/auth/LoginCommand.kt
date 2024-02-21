package org.example.commands.auth

import org.example.Application
import org.example.commands.Command
import org.example.entities.auth.User
import org.example.states.MainMenuState
import org.example.utils.ioHelpers.AuthInputHelper
import org.example.utils.ioHelpers.ConsoleInputHelper
import org.example.utils.ioHelpers.ConsoleOutputHelper
import org.example.utils.ioHelpers.OutputMessageType

/**
 * Команда, переводящая приложение в состояние атворизации пользователя.
 */
class LoginCommand(override var description: String = "Войти в аккаунт") : Command<Application> {
    override fun execute(argument: Application) {
        if (argument.session.user != null) {
            println("В системе уже есть авторизованный пользователь. Чтобы начать новую сессию, закончите текущую.")
            argument.state = MainMenuState(argument, argument.state)
            ConsoleInputHelper.readEnterPress()
            return
        }

        val userToLogin: User =
            AuthInputHelper.readExistingUserByLogin(argument.userStorage, argument.backCommand) ?: return

        val password: String = AuthInputHelper.readPasswordForUser(userToLogin, argument.backCommand) ?: return

        if (!argument.session.authorizeUser(userToLogin, password)) {
            ConsoleOutputHelper.printMessage("Во время авторизации произошла ошибка!", OutputMessageType.Error)
            ConsoleInputHelper.readEnterPress()
            return
        }

        ConsoleOutputHelper.printMessage(
            "Успешная авторизация пользователя ${argument.session.user!!.login}",
            OutputMessageType.Success)
        println()
        ConsoleInputHelper.readEnterPress()

        argument.state = MainMenuState(argument, argument.state)
    }
}