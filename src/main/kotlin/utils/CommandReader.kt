package org.example.utils

import org.example.commands.Command
import org.example.factories.commands.CommandFactory
import org.example.utils.ioHelpers.ConsoleInputHelper
import org.example.utils.ioHelpers.ConsoleOutputHelper
import org.example.utils.ioHelpers.OutputMessageType

/**
 * Предоставляет механизм для хранения и выполнения команд пользователя.
 * Изначально список команд пуст, в него необходимо загрузить команды, используя функцию loadCommands.
 * Это сделано, чтобы не нужно было пересоздавать объект при необходимости изменения списка команд.
 * @param T тип аргумента команды пользователя.
 */
class CommandReader<T>(userCommandSetFactory: CommandFactory<T>) {
    private val _commands: MutableMap<Int, Command<T>> = mutableMapOf()

    init {
        var commandNumber = 1
        userCommandSetFactory.createCommandSet().forEach{_commands[commandNumber++] = it}
    }

    /**
     * Отобразить все доступные пользователю команды.
     */
    fun displayCommands() {
        if (_commands.isEmpty()) {
            println("Нет доступных команд.")
            return
        }

        println("Доступные команды:")
        _commands.forEach{ println("${it.key}. ${it.value.description}") }
    }

    /**
     * Считать у пользователя команду и выполнить её.
     * @param argument аргумент, передаваемый выбранной пользователем команде.
     */
    fun readAndExecute(argument: T) {
        if (_commands.isEmpty()) {
            return
        }

        var toExecute: Command<T>? = null

        do {
            toExecute = _commands[ConsoleInputHelper.readInt("Введите номер команды: ")]

            if (toExecute == null) {
                ConsoleOutputHelper.printMessage("Команды с таким номером нет в списке!", OutputMessageType.Error)
            }
        } while (toExecute == null)

        toExecute.execute(argument)
    }
}