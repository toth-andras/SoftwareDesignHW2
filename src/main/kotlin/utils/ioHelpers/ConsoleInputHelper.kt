package org.example.utils.ioHelpers

import org.example.crud.menu.MenuItemStorage
import org.example.entities.menu.MenuItem

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
                    ConsoleOutputHelper.printMessage(
                        "Строка не может быть пустой, повторите ввод!",
                        OutputMessageType.Error
                    )
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

        /**
         * Считывает от пользователя набор блюд и ожидаемое количетво их порций
         */
        fun readMenuItems(menuStorage: MenuItemStorage, backCommand: String): List<Pair<MenuItem, Int>>? {
            val items: MutableList<Pair<MenuItem, Int>> = mutableListOf()

            do {
                val menuItemId: Int = readIntCheckBackCommand("Введите номер блюда, которое хотели" +
                        " бы добавить в заказ, либо -1, чтобы прекратить ввод: ", backCommand) ?: return null

                if (menuItemId == -1) {
                    break
                }

                val menuItem = menuStorage.getMenuItem(menuItemId)
                if (menuItem == null) {
                    ConsoleOutputHelper.printMessage("Блюда с таким номером нет в меню!", OutputMessageType.Error)
                    continue
                }

                ConsoleOutputHelper.printMessage("Доступно ${menuItem.quantity} порций блюда")

                val num = readPortionsNumber(menuItem, backCommand) ?: return null
                items.addLast(Pair(menuItem, num))
            } while (menuItemId != -1)

            return items
        }

        private fun readPortionsNumber(menuItem: MenuItem, backCommand: String): Int? {
            var portions: Int? = null
            do {
                portions = ConsoleInputHelper.readIntCheckBackCommand("Введите количество порций: ", backCommand) ?: return null

                if (portions <= 0) {
                    ConsoleOutputHelper.printMessage("Количество порций должно быть положительным!", OutputMessageType.Error)
                    portions = null
                    continue
                }

                if (portions > menuItem.quantity) {
                    ConsoleOutputHelper.printMessage("Доступно всего ${menuItem.quantity} порций!", OutputMessageType.Error)
                    portions = null
                    continue
                }
            } while (portions == null)

            return portions
        }
    }
}