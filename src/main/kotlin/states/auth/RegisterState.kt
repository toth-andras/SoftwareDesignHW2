package org.example.states.auth

import org.example.Application
import org.example.states.ApplicationState
import org.example.states.InitialState
import org.example.utils.ConsoleInputHelper
import org.example.utils.ConsoleOutputHelper
import org.example.utils.OutputMessageType

/**
 * Состояние приложения, в котором пользователь создает новую учётную запись.
 */
class RegisterState(application: Application, previousState: ApplicationState? = null): ApplicationState(application, previousState) {
    override fun process() {
        val login: String? = readLogin()
        if (login == null) {
            application.state = InitialState(application)
            return
        }

        val password: String? = readPassword()
        if (password == null) {
            application.state = InitialState(application)
            return
        }

        val isAdmin: Boolean? = readIsAdmin()
        if (isAdmin == null) {
            application.state = InitialState(application)
            return
        }

        val user = application.userStorage.createUser(login, password, isAdmin)
        ConsoleOutputHelper.printMessage("Учетная запись ${user.login} создана!", OutputMessageType.Success)
        ConsoleInputHelper.readEnterPress()
        application.state = previousState
    }

    /**
     * Считывает логин пользователя.
     * @return логин или null, если пользователь ввел команду назад.
     */
    private fun readLogin(): String? {
        var login = ""
        do {
            login = ConsoleInputHelper.readNotEmptyString("Введите логин нового пользователя: ")
            if (login == application.backCommand) {
                application.state = previousState
                return null
            }

            if (application.userStorage.loginExists(login)) {
                ConsoleOutputHelper.printMessage("Данный логин уже занят другим пользователем!", OutputMessageType.Error)
                login = ""
            }
        } while (login == "")

        return login
    }

    /**
     * Считывает пароль пользователя.
     * @return пароль или null, если пользователь ввел команду назад.
     */
    private fun readPassword(): String? {
        // Ввод пароля и его повтора (массив и цикл применены, чтобы не дублировать код проверки на команду назад).
        val passwords: Array<String> = arrayOf("", "")
        do {
            var hint = "Введите пароль: "
            for (i in 0..1) {
                passwords[i] = ConsoleInputHelper.readNotEmptyString(hint)
                hint = "Введите пароль (повторно): "

                if (passwords[i] == application.backCommand) {
                    application.state = previousState
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
    private fun readIsAdmin(): Boolean? {
        print("Является ли пользователь администратором (Y/N): ")
        val input = readln()
        return if (input == application.backCommand) null else input == "Y"
    }
}