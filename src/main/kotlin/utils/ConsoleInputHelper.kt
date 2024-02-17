package org.example.utils

/**
 * Представляет функционал для считывания информации с консоли.
 */
class ConsoleInputHelper {
    companion object {
        /**
         * Ожидает, пока пользователь не нажмет на Enter.
         */
        fun readEnterPress(message: String = "Для продолжения нажмиет Enter") {
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
                if (str.trim().isEmpty()) {
                    ConsoleOutputHelper.printMessage("Строка не может быть пустой, повторите ввод!", OutputMessageType.Error)
                    str = ""
                }
            } while (str == "")

            return str
        }


        /**
         * Считывает у пользователя целое число.
         * @param message подсказка пользователю.
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
    }
}