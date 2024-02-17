package org.example.commands.menu

import org.example.Application
import org.example.commands.Command
import org.example.entities.menu.MenuItem
import org.example.utils.ConsoleInputHelper
import org.example.utils.ConsoleOutputHelper
import org.example.utils.OutputMessageType

/**
 * Команда удалению блюда из меню.
 */
class RemoveMenuItemCommand(override var description: String = "Удалить блюдо") : Command<Application> {
    override fun execute(argument: Application) {
        // TODO: подумать про проверку меню на пустоту
        var itemToRemove: MenuItem? = null

        do {
            val id = ConsoleInputHelper.readIntCheckBackCommand("Введите id блюда, которое хотите удалить: ", argument.backCommand)
                ?: return

            itemToRemove = argument.menuStorage.getMenuItem(id)
            if (itemToRemove == null) {
                ConsoleOutputHelper.printMessage("Блюда с таким id нет в меню!", OutputMessageType.Error)
            }
        } while (itemToRemove == null)

        if (argument.menuStorage.removeMenuItem(itemToRemove.id)) {
            ConsoleOutputHelper.printMessage("Блюдо удалено из меню", OutputMessageType.Success)
            // TODO: продумать, что делать с заказами с удаленным блюдом
            ConsoleOutputHelper.printMessage("Учтите, что все оформленные заказы с данным блюдом")
            ConsoleInputHelper.readEnterPress()
        }
    }
}