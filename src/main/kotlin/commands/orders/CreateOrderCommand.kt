package org.example.commands.orders

import org.example.Application
import org.example.commands.Command
import org.example.entities.menu.MenuItem
import org.example.utils.ConsoleInputHelper
import org.example.utils.ConsoleOutputHelper
import org.example.utils.OutputMessageType
import org.example.utils.menuRepresentation.MenuItemVisitorPresentation
import org.example.utils.orderPresentation.OrderVisitorPresentation

/**
 * Команда создания заказа.
 */
class CreateOrderCommand(override var description: String = "Добавить заказ") : Command<Application> {
    override fun execute(argument: Application) {
        ConsoleOutputHelper.displayMenu(argument.menuStorage.getMenuItems(), MenuItemVisitorPresentation())
        println()
        val orderItems: MutableList<MenuItem> = mutableListOf()

        do {
            val menuItemId: Int = ConsoleInputHelper.readIntCheckBackCommand("Введите номер блюда, которое хотели" +
                    " бы добавить в заказ, либо -1, чтобы оформить заказ: ", argument.backCommand) ?: return

            if (menuItemId == -1) {
                break
            }

            val menuItem = argument.menuStorage.getMenuItem(menuItemId)
            if (menuItem == null) {
                ConsoleOutputHelper.printMessage("Блюда с таким номером нет в меню!", OutputMessageType.Error)
                continue
            }

            ConsoleOutputHelper.printMessage("Доступно ${menuItem.quantity} порций блюда")

            val num = readPortionsNumber(menuItem, argument.backCommand) ?: return
            menuItem.order(num)
            for (i in 1..num) {
                orderItems.addLast(menuItem)
            }
        } while (menuItemId != -1)

        if (orderItems.isEmpty()) {
            ConsoleOutputHelper.printMessage("Нельзя создать заказ без блюд!", OutputMessageType.Error)
            println()
            ConsoleInputHelper.readEnterPress()
            return
        }

        val order = argument.orderStorage.createOrder(argument.session.user!!, orderItems)

        println("Ваш заказ: ")
        println(OrderVisitorPresentation().presentOrder(order))
        println()
        ConsoleInputHelper.readEnterPress()
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