package org.example.commands.orders

import org.example.Application
import org.example.commands.Command
import org.example.entities.orders.Order
import org.example.entities.orders.OrderStatus
import org.example.utils.ioHelpers.ConsoleInputHelper
import org.example.utils.ioHelpers.ConsoleOutputHelper
import org.example.utils.ioHelpers.OutputMessageType
import org.example.utils.orderPresentation.OrderVisitorShortPresentation

/**
 * Позволяет пользователю добавить в заказ блюдо.
 */
class ModifyOrderCommand(override var description: String = "Добавить блюдо в существующий заказ") : Command<Application> {
    override fun execute(argument: Application) {
        ConsoleOutputHelper.displayOrders(argument.orderStorage.getUserOrders(argument.session.user!!.id), OrderVisitorShortPresentation())
        println()

        var orderToChange: Order? = null
        do {
            val id = ConsoleInputHelper.readIntCheckBackCommand("Введите номер заказа, в который хотели бы добавить блюдо: ", argument.backCommand) ?: return
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


    }
}