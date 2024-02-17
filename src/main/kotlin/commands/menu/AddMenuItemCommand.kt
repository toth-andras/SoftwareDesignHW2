package org.example.commands.menu

import org.example.Application
import org.example.commands.Command
import org.example.utils.ConsoleInputHelper
import org.example.utils.ConsoleOutputHelper
import org.example.utils.OutputMessageType

/**
 * Команда добавления блюда в хранилище.
 */
class AddMenuItemCommand(override var description: String = "Добавить блюдо") : Command<Application> {
    override fun execute(argument: Application) {
        val name: String = readName(argument) ?: return
        val price: Int = readPositiveInt("Введите цену за одну порцию: ", argument.backCommand) ?: return
        val quantity: Int = readPositiveInt("Введите количество порций в наличии: ", argument.backCommand) ?: return
        val timeToCook: Int = readPositiveInt("Введите время приготовления одной порции в минутах: ", argument.backCommand) ?: return

        argument.menuStorage.createMenuItem(name, quantity, price, timeToCook)
        ConsoleOutputHelper.printMessage("Блюдо добавлено в меню", OutputMessageType.Success)
        ConsoleInputHelper.readEnterPress()
    }

    private fun readName(application: Application): String? {
        var name: String? = null
        do {
            name = ConsoleInputHelper.readNotEmptyString("Введите название блюда: ")
            if (name == application.backCommand) {
                return null
            }
            if (application.menuStorage.getMenuItems().any{it.name == name}) {
                ConsoleOutputHelper.printMessage("Блюдо с таким названием уже есть в меню!", OutputMessageType.Error)
                name = null
            }
        } while(name == null)

        return name
    }
    private fun readPositiveInt(message: String, backCommand: String): Int? {
        var num: Int? = null
        do {
            num = ConsoleInputHelper.readIntCheckBackCommand(message, backCommand)
            if (num == null) {
                return null
            }

            if (num <= 0) {
                ConsoleOutputHelper.printMessage("Число должно быть положительным!", OutputMessageType.Error)
                num = null
            }

        } while (num == null)

        return num
    }
}