package org.example.commands.orders

import org.example.Application
import org.example.commands.Command
import org.example.entities.menu.MenuItem
import org.example.entities.orders.Order
import org.example.entities.orders.OrderStatus
import org.example.utils.ioHelpers.ConsoleInputHelper
import org.example.utils.ioHelpers.ConsoleOutputHelper
import org.example.utils.ioHelpers.OutputMessageType
import org.example.utils.menuRepresentation.MenuItemVisitorPresentation
import org.example.utils.orderPresentation.OrderVisitorPresentation
import org.example.utils.orderPresentation.OrderVisitorShortPresentation

/**
 * Позволяет пользователю добавить в заказ блюдо.
 */
class ModifyOrderCommand(override var description: String = "Изменить существующий заказ") : Command<Application> {
    override fun execute(argument: Application) {
        ConsoleOutputHelper.displayOrders(argument.orderStorage.getUserOrders(argument.session.user!!.id), OrderVisitorShortPresentation())
        println()

        val orderToChange = readOrder(argument) ?: return

        // Меняем статус заказа на специальный статус, чтобы заказ не был принят
        // в обработку в то время, когда модифицируется.
        val previousStatus = orderToChange.status
        orderToChange.status = OrderStatus.UnderModification

        ConsoleOutputHelper.displayMenu(argument.menuStorage.getMenuItems().filter { it.quantity > 0 }, MenuItemVisitorPresentation())
        println()

        val orderItems: List<Pair<MenuItem, Int>>? = ConsoleInputHelper.readMenuItems(argument.menuStorage, argument.backCommand)
        if (orderItems.isNullOrEmpty()) {
            orderToChange.status = previousStatus
            return
        }
        orderItems.forEach {
            it.first.order(it.second)
            for (i in 1..it.second) {
                orderToChange.addMenuItem(it.first)
            }
        }

        orderToChange.status = previousStatus
        ConsoleOutputHelper.printMessage("Заказ обновлен", OutputMessageType.Success)
        println("Ваш заказ:")
        println(OrderVisitorPresentation().presentOrder(orderToChange))
        println()
        ConsoleInputHelper.readEnterPress()
    }

    private fun readOrder(argument: Application): Order? {
        var orderToChange: Order? = null
        do {
            val id = ConsoleInputHelper.readIntCheckBackCommand("Введите номер заказа, в который хотели бы добавить блюдо: ", argument.backCommand) ?: return null
            orderToChange = argument.orderStorage.getOrder(id)
            if (orderToChange == null) {
                ConsoleOutputHelper.printMessage("Нет заказа с таким номером!", OutputMessageType.Error)
                continue
            }
            if (orderToChange.userId != argument.session.user!!.id) {
                orderToChange = null
                ConsoleOutputHelper.printMessage("Нет прав модификации данного заказа!", OutputMessageType.Error)
                continue
            }
            if (orderToChange.status != OrderStatus.Created && orderToChange.status != OrderStatus.OnCook) {
                orderToChange = null
                ConsoleOutputHelper.printMessage("Нельзя изменять заказ после того, как он готов!", OutputMessageType.Error)
                continue
            }
        } while (orderToChange == null)

        return orderToChange
    }
}