package org.example.utils

/**
 * Представляет функционал для считывания информации с консоли.
 */
class ConsoleInputHelper {
    companion object {
        /**
         * Ожидает, пока пользователь не нажмет на Enter.
         */
        fun readEnterPress(message: String = "Для продолжения нажмите Enter") {
            println(message)
            readln()
        }

        /**
         * Считывает с консоли непустую строку.
         * @param message подсказка пользователю.
         * @return непустая строка.
         */
        fun readNotEmptyString(message: String): String {
            var str = ""
            do {
                print(message)
                str = readln()
                if (str.trim().isBlank()) {
                    ConsoleOutputHelper.printMessage("Строка не может быть пустой, повторите ввод!", OutputMessageType.Error)
                    str = ""
                }
            } while (str == "")

            return str
        }


        /**
         * Считывает у пользователя целое число.
         * @param message подсказка пользователю.
         * @return считанное число.
         */
        fun readInt(message: String): Int {
            var number: Int? = null
            do {
                print(message)
                val numStr = readln()

                number = numStr.toIntOrNull()
                if (number == null) {
                    ConsoleOutputHelper.printMessage("Неправильный формат ввода!", OutputMessageType.Error)
                }
            } while (number == null)

            return number
        }

        /**
         * Считывает у пользователя целое число, пока он не введет его или команду возврата к предыдущему состоянию.
         * @param message подсказка пользователю.
         * @param backCommand команда возврата к предыдущему состоянию.
         * @return считанное число или null, если пользователь ввел команду возврата к предыдущему состоянию.
         */
        fun readIntCheckBackCommand(message: String, backCommand: String): Int? {
            var number: Int? = null

            do {
                print(message)
                val numStr = readln()
                if (numStr == backCommand) {
                    return null
                }

                number = numStr.toIntOrNull()
                if (number == null) {
                    ConsoleOutputHelper.printMessage("Неправильный формат ввода!", OutputMessageType.Error)
                }
            } while (number == null)

            return number
        }
    }
}