package org.example.commands.orders

import org.example.Application
import org.example.commands.Command
import org.example.entities.orders.Order
import org.example.entities.orders.OrderStatus
import org.example.utils.ConsoleInputHelper
import org.example.utils.ConsoleOutputHelper
import org.example.utils.OutputMessageType
import org.example.utils.orderPresentation.OrderAdminPresentation
import org.example.utils.orderPresentation.OrderVisitorShortPresentation

/**
 * Команда, отменяющая заказ. Отменить заказ может его владелец или администратор.
 */
class CancelOrderCommand(override var description: String = "Отменить заказ") : Command<Application> {
    override fun execute(argument: Application) {
        val isAdmin = argument.session.user!!.isAdmin

        val statusFilter: (Order) -> Boolean = {it.status == OrderStatus.Created || it.status == OrderStatus.OnCook}
        val orders =
            if (isAdmin) argument.orderStorage.getOrders().filter(statusFilter)
            else argument.orderStorage.getUserOrders(argument.session.user!!.id).filter(statusFilter)

        val presenter = if (isAdmin) OrderAdminPresentation(argument.userStorage) else OrderVisitorShortPresentation()
        ConsoleOutputHelper.displayOrders(orders, presenter)
        println()

        var orderToCancel: Order? = null
        do {
            val id = ConsoleInputHelper.readIntCheckBackCommand("Введите номер заказа, который хотите отменить: ", argument.backCommand) ?: return

            orderToCancel = argument.orderStorage.getOrder(id)
            if (orderToCancel == null) {
                ConsoleOutputHelper.printMessage("Заказа с таким номером нет!", OutputMessageType.Error)
                continue
            }

            if (orderToCancel.userId != argument.session.user!!.id && !argument.session.user!!.isAdmin) {
                ConsoleOutputHelper.printMessage("У вас нет прав на отмену данного заказа!")
                orderToCancel = null
                continue
            }

            if (orderToCancel.status != OrderStatus.Created && orderToCancel.status != OrderStatus.OnCook) {
                ConsoleOutputHelper.printMessage("Нельзя отменить заказ после того, как он готов!", OutputMessageType.Error)
                orderToCancel = null
                continue
            }
        } while (orderToCancel == null)

        orderToCancel.status = OrderStatus.Cancelled
        ConsoleOutputHelper.printMessage("Заказ отменён", OutputMessageType.Success)
        ConsoleInputHelper.readEnterPress()
    }

}