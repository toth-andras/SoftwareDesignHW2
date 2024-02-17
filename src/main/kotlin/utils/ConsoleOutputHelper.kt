package org.example.utils

import org.example.entities.menu.MenuItem


/**
 * Виды специальны сообщений пользователю.
 */
enum class OutputMessageType {Error, Warning, Success, Info}

/**
 * Представляет функционал для вывода информации на консоль.
 */
class ConsoleOutputHelper {
    companion object {
        /**
         * Вывести на экран сообщение об ошибке.
         * @param message сообщение об ошибке.
         */
        fun printMessage(message: String, messageType: OutputMessageType = OutputMessageType.Info) {
            val color: String = when (messageType) {
                OutputMessageType.Error -> "\u001b[31m"
                OutputMessageType.Warning -> "\u001b[33m"
                OutputMessageType.Success -> "\u001B[32m"
                OutputMessageType.Info -> "\u001B[34m"
                else -> "\u001B[37m"
            }
            val reset = "\u001b[0m"

            println(color + message + reset)
        }

        /**
         * Очистить консоль.
         * К сожалению, терминал IntelliJ не поддерживает очистку с помощью cls,
         * поэтому приходится просто сдвигать вниз.
         */
        fun clearConsole() {
            println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n")
        }

        /**
         * Отображает на экран содержимое меню
         */
        fun displayMenu(menu: Iterable<MenuItem>, interpreter: (MenuItem) -> String = {it.toString()}) {
            println("================================  \uD83C\uDF7D\uFE0F Меню ================================")
            if (!menu.any()) {
                println("В меню не добавлено ни одно блюдо")
                return
            }
            menu.forEach{ "||" + println(interpreter(it)) + "||"}
            println("==========================================================================================")
        }
    }
}