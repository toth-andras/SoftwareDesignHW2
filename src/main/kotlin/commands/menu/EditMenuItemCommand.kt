package org.example.commands.menu

import org.example.Application
import org.example.commands.Command
import org.example.crud.menu.MenuItemStorage
import org.example.entities.menu.MenuItem
import org.example.utils.ConsoleInputHelper
import org.example.utils.ConsoleOutputHelper
import org.example.utils.OutputMessageType

/**
 * Команда модификациии данных блюда.
 */
class EditMenuItemCommand(override var description: String = "Изменить блюдо") : Command<Application> {
    override fun execute(argument: Application) {
        var menuItemToEdit: MenuItem? = null

        do {
            val id = ConsoleInputHelper.readIntCheckBackCommand("Введите id блюда: ", argument.backCommand) ?: return
            menuItemToEdit = argument.menuStorage.getMenuItem(id)

            if (menuItemToEdit == null) {
                ConsoleOutputHelper.printMessage("Блюда с таким id нет!", OutputMessageType.Error)
            }
        } while (menuItemToEdit == null)

        ConsoleOutputHelper.printMessage("Модификация блюда ${menuItemToEdit.name}:")

        val newName = readNewName(
            "Введите новое название блюда. Чтобы оставить прежнее значение (${menuItemToEdit.name}), введите '-': ",
            menuItemToEdit.name,
            argument.backCommand,
            argument.menuStorage)
        menuItemToEdit.name = newName ?: return

        val newPrice = readNewPositiveInt(
            "Введите новую цену за порцию. Чтобы оставить прежнее значение (${menuItemToEdit.price}), введите '-': ",
            menuItemToEdit.price,
            argument.backCommand)
        menuItemToEdit.price = newPrice ?: return

        val newQuantity = readNewPositiveInt(
            "Введите новое количество порций. Чтобы оставить прежнее значение (${menuItemToEdit.quantity}), введите '-': ",
            menuItemToEdit.quantity,
            argument.backCommand)
        menuItemToEdit.quantity = newQuantity ?: return

        val newTime = readNewPositiveInt(
            "Введите новое время готовки порции. Чтобы оставить прежнее значение (${menuItemToEdit.timeToCook}), введите '-': ",
            menuItemToEdit.timeToCook,
            argument.backCommand)
        menuItemToEdit.timeToCook = newTime ?: return

        ConsoleOutputHelper.printMessage("Данные изменены", OutputMessageType.Success)
        ConsoleOutputHelper.printMessage("Все заказы, содержащие прежние данные этого блюда, обновлены не будут")
        ConsoleInputHelper.readEnterPress()
    }

    private fun readNewName(message: String,
                    defaultValue: String,
                    backCommand: String,
                    menuStorage: MenuItemStorage,
                    defaultIndicator: String = "-"): String?
    {
        var value: String? = null
        do {
            value = ConsoleInputHelper.readNotEmptyString(message)

            if (value == backCommand) {
                return null
            }
            if (value == defaultValue || value == defaultIndicator) {
                return defaultValue
            }
            if (menuStorage.getMenuItems().any{it.name == value}) {
                value = null
                ConsoleOutputHelper.printMessage("В меню уже есть блюдо с таким названием!", OutputMessageType.Error)
            }
        } while (value == null)

        return value
    }

    private fun readNewPositiveInt(message: String, defaultValue: Int, backCommand: String, defaultIndicator: String = "-"): Int? {
        var number: Int?
        do {
            print(message)
            val numberStr = readln()
            if (numberStr == backCommand) {
                return null
            }
            if (numberStr == defaultIndicator) {
                return defaultValue
            }
            number = numberStr.toIntOrNull()
            if (number == null) {
                ConsoleOutputHelper.printMessage("Неправильный формат ввода!", OutputMessageType.Error)
                continue
            }
            if (number <= 0) {
                number = null
                ConsoleOutputHelper.printMessage("Число должно быть положительным!", OutputMessageType.Error)
            }
        } while (number == null)

        return number
    }
}