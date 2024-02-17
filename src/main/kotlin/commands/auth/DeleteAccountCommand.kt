package org.example.commands.auth

import org.example.Application
import org.example.commands.Command
import org.example.entities.auth.User
import org.example.states.InitialState
import org.example.utils.AuthInputHelper
import org.example.utils.ConsoleInputHelper
import org.example.utils.ConsoleOutputHelper
import org.example.utils.OutputMessageType

/**
 * Команда, переводящая приложение в состояние удаления аккаунта.
 */
class DeleteAccountCommand(override var description: String = "Удалить учётную запись") : Command<Application> {
    override fun execute(argument: Application) {
        val userToDelete: User = AuthInputHelper.readExistingUserByLogin(argument.userStorage, argument.backCommand)
            ?: return

        // Убедились, что удаляющий знает пароль.
        AuthInputHelper.readPasswordForUser(userToDelete, argument.backCommand) ?: return

        if (!argument.userStorage.removeUser(userToDelete.id)) {
            ConsoleOutputHelper.printMessage("Не удалось удалить учетную запись ${userToDelete.login}", OutputMessageType.Error)
            ConsoleInputHelper.readEnterPress()
            return
        }

        if (argument.session.user == userToDelete) {
            argument.session.deAuthorizeUser()
            argument.state = InitialState(argument)
        }

        ConsoleOutputHelper.printMessage("Учетная запись ${userToDelete.login} удалена", OutputMessageType.Success)
        ConsoleInputHelper.readEnterPress()
    }
}