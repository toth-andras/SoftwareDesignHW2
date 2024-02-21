package org.example.factories.commands

import org.example.commands.Command

/**
 * Представляет обобщенный интерфейс для классов, создающих наборы команд для CommandReader.
 * @param T аргумент команд порождаемого набора.
 */
interface CommandFactory<T> {
    /**
     * Получить набор команд пользователя.
     */
    fun createCommandSet(): Iterable<Command<T>>
}