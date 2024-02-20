package org.example.utils.ioHelpers

import org.example.crud.auth.UserStorage
import org.example.entities.auth.User

/**
 * Предоставляет  функционал для ввода пользователем информации об учётных записях.
 */
class AuthInputHelper {
    companion object {
        /**
         * Запрашивает от пользователя логин, пока он не введет существующий логин или не введет команду
         * возврата к предыдущему состоянию.
         * @param userStorage хранилище с пользовательским объектами.
         * @param backCommand команда возврата к предыдущему состоянию.
         * @return объект пользователя со считанным аккаунтом или null, если пользователь ввел команду
         * возврата к предыдущему состоянию.
         */
        fun readExistingUserByLogin(userStorage: UserStorage, backCommand: String): User? {
            var user: User? = null

            do {
                val login = ConsoleInputHelper.readNotEmptyString("Введите логин: ")
                if (login == backCommand) {
                    return null
                }

                if (userStorage.loginExists(login)) {
                    user = userStorage.getUser(login)
                    continue
                }

                ConsoleOutputHelper.printMessage("Пользователя с таким логином нет!", OutputMessageType.Error)
            } while (user == null)

            return user
        }

        /**
         * Запрашивает от пользователя пароль от переданного аккаунта, пока он не
         * введет его верно или не введет команду возврата к предыдущему состоянию.
         * @param user аккаунт, для котого надо считать пароль.
         * @param backCommand команда возврата к предыдущему состоянию.
         * @return пароль от переданного аккаунта или null, если пользователь
         * ввел команду возврата к предыдущему состоянию.
         */
        fun readPasswordForUser(user: User, backCommand: String): String? {
            var password: String? = null

            do {
                password = ConsoleInputHelper.readNotEmptyString("Введите пароль: ")
                if (password == backCommand) {
                    return null
                }
                if (user.checkPassword(password)) {
                    continue
                }
                password = null
                ConsoleOutputHelper.printMessage("Неправильный пароль!", OutputMessageType.Error)
            } while (password == null)

            return password
        }
    }
}