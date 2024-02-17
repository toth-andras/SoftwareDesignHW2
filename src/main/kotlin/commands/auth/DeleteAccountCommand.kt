package org.example.commands.auth

import org.example.Application
import org.example.commands.Command
import org.example.states.auth.DeleteAccountState

/**
 * Команда, переводящая приложение в состояние удаления аккаунта.
 */
class DeleteAccountCommand(override var description: String = "Удалить учётную запись") : Command<Application> {
    override fun execute(argument: Application) {
        argument.state = DeleteAccountState(argument, argument.state)
    }
}