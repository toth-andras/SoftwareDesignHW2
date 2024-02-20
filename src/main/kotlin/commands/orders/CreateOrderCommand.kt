package org.example.commands.orders

import org.example.Application
import org.example.commands.Command
import org.example.entities.menu.MenuItem
import org.example.utils.ioHelpers.ConsoleInputHelper
import org.example.utils.ioHelpers.ConsoleOutputHelper
import org.example.utils.ioHelpers.OutputMessageType
import org.example.utils.menuRepresentation.MenuItemVisitorPresentation
import org.example.utils.orderPresentation.OrderVisitorPresentation

/**
 * Команда создания заказа.
 */
class CreateOrderCommand(override var description: String = "Добавить заказ") : Command<Application> {
    override fun execute(argument: Application) {
        ConsoleOutputHelper.displayMenu(argument.menuStorage.getMenuItems(), MenuItemVisitorPresentation())
        println()
        val orderItems: List<Pair<MenuItem, Int>> = ConsoleInputHelper.readMenuItems(argument.menuStorage, argument.backCommand) ?: return
        if (orderItems.isEmpty()) {
            ConsoleOutputHelper.printMessage("Нельзя создать заказ без блюд!", OutputMessageType.Error)
            ConsoleInputHelper.readEnterPress()
            return
        }
        val menuItems: MutableList<MenuItem> = mutableListOf()
        orderItems.forEach {
            it.first.order(it.second)
            for (i in 1..it.second) {
                menuItems.addLast(it.first)
            }
        }

        val order = argument.orderStorage.createOrder(argument.session.user!!, menuItems)

        println("Ваш заказ: ")
        println(OrderVisitorPresentation().presentOrder(order))
        println()
        ConsoleInputHelper.readEnterPress()
    }
}