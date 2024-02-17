package org.example.commands.auth

import org.example.Application
import org.example.commands.Command
import org.example.crud.auth.UserStorage
import org.example.utils.ConsoleInputHelper
import org.example.utils.ConsoleOutputHelper
import org.example.utils.OutputMessageType

/**
 * Команда, переводящая приложение в состояние регистрации пользователя.
 */
class RegisterCommand(override var description: String = "Регистрация") : Command<Application> {
    override fun execute(argument: Application) {
        val login: String = readLogin(argument.userStorage, argument.backCommand) ?: return
        val password: String = readPassword(argument.backCommand) ?: return
        val isAdmin: Boolean = readIsAdmin(argument.backCommand) ?: return

        val user = argument.userStorage.createUser(login, password, isAdmin)
        ConsoleOutputHelper.printMessage("Учетная запись ${user.login} создана!", OutputMessageType.Success)
        ConsoleInputHelper.readEnterPress()
    }

    private fun readLogin(userStorage: UserStorage, backCommand: String): String? {
        var login: String?
        do {
            login = ConsoleInputHelper.readNotEmptyString("Введите логин нового пользователя: ")
            if (login == backCommand) {
                return null
            }
            if (userStorage.loginExists(login)) {
                login = null
                ConsoleOutputHelper.printMessage("Данный логин уже занят другим пользователем!", OutputMessageType.Error)
            }
        } while (login == null)

        return login
    }

    private fun readPassword(backCommand: String): String? {
        // Ввод пароля и его повтора (массив и цикл применены, чтобы не дублировать код проверки на команду назад).
        val passwords: Array<String> = arrayOf("", "")
        do {
            var hint = "Введите пароль: "
            for (i in 0..1) {
                passwords[i] = ConsoleInputHelper.readNotEmptyString(hint)
                hint = "Введите пароль (повторно): "

                if (passwords[i] == backCommand) {
                    return null
                }
            }

            if (passwords[0] != passwords[1]) {
                ConsoleOutputHelper.printMessage("Пароль и его повтор не совпали!", OutputMessageType.Error)
            }
        } while (passwords[0] != passwords[1])

        return passwords[0]
    }

    /**
     * Считывает, является ли пользователь администратором.
     * @return является ли пользователь администратором или null, если пользователь ввел команду назад.
     */
    private fun readIsAdmin(backCommand: String): Boolean? {
        print("Является ли пользователь администратором (Y/N): ")
        val input = readln()
        return if (input == backCommand) null else input == "Y"
    }
}