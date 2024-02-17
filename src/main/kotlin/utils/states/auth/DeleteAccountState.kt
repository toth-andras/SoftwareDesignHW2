package org.example.states.auth

import org.example.Application
import org.example.entities.auth.User
import org.example.states.ApplicationState
import org.example.states.InitialState
import org.example.utils.AuthInputHelper
import org.example.utils.ConsoleInputHelper
import org.example.utils.ConsoleOutputHelper
import org.example.utils.OutputMessageType

/**
 * Состояние приложения, в котором пользователь удаляет аккаунт.
 */
class DeleteAccountState(application: Application, previousState: ApplicationState?): ApplicationState(application, previousState) {
    override fun process() {
        val userToDelete: User? = AuthInputHelper.readExistingUserByLogin(application.userStorage, application.backCommand)
        if (userToDelete == null) {
            application.state = previousState
            return
        }

        val password: String? = AuthInputHelper.readPasswordForUser(userToDelete, application.backCommand)
        if (password == null) {
            application.state = previousState
            return
        }

        if (!application.userStorage.removeUser(userToDelete.id)) {
            ConsoleOutputHelper.printMessage("Не удалось удалить учетную запись ${userToDelete.login}", OutputMessageType.Error)
            ConsoleInputHelper.readEnterPress()
            return
        }

        if (application.session.user == userToDelete) {
            application.session.deAuthorizeUser()
            application.state = InitialState(application)
        }

        ConsoleOutputHelper.printMessage("Учетная запись ${userToDelete.login} удалена", OutputMessageType.Success)
        ConsoleInputHelper.readEnterPress()
        application.state = previousState
    }
}