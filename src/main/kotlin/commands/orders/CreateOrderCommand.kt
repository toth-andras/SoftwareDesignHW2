package org.example.commands.orders

import org.example.Application
import org.example.commands.Command

/**
 * Команда создания заказа.
 */
class CreateOrderCommand(override var description: String) : Command<Application> {
    override fun execute(argument: Application) {

    }
}