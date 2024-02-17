package org.example.states.auth

import org.example.Application
import org.example.states.ApplicationState
import org.example.entities.auth.User
import org.example.states.MainMenuState
import org.example.utils.AuthInputHelper
import org.example.utils.ConsoleInputHelper
import org.example.utils.ConsoleOutputHelper
import org.example.utils.OutputMessageType

/**
 * Состояние приложения, в котором пользователь авторизуется.
 */
class LoginState(application: Application, previousState: ApplicationState? = null): ApplicationState(application, previousState) {
    override fun process() {
        if (application.session.user != null) {
            println("В системе уже есть авторизованный пользователь. Чтобы начать новую сессию, закончите текущую.")
            ConsoleInputHelper.readEnterPress()
            application.state = previousState
        }

        val userToLogin: User? = AuthInputHelper.readExistingUserByLogin(application.userStorage, application.backCommand)
        if (userToLogin == null) {
            application.state = previousState
            return
        }

        val password: String? = AuthInputHelper.readPasswordForUser(userToLogin, application.backCommand)
        if (password == null) {
            application.state = previousState
            return
        }

        if (!application.session.authorizeUser(userToLogin, password)) {
            ConsoleOutputHelper.printMessage("Во время авторизации произошла ошибка!", OutputMessageType.Error)
            ConsoleInputHelper.readEnterPress()
            return
        }

        ConsoleOutputHelper.printMessage(
            "Успешная авторизация пользователя ${application.session.user!!.login}",
            OutputMessageType.Success)
        ConsoleInputHelper.readEnterPress()

        application.state = MainMenuState(application, previousState)
    }
}