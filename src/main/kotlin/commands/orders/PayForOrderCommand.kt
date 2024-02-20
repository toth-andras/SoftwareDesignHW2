package org.example.commands.orders

import org.example.Application
import org.example.commands.Command
import org.example.entities.orders.Order
import org.example.entities.orders.OrderStatus
import org.example.utils.ioHelpers.ConsoleInputHelper
import org.example.utils.ioHelpers.ConsoleOutputHelper
import org.example.utils.ioHelpers.OutputMessageType
import org.example.utils.orderPresentation.OrderVisitorPresentation
import org.example.utils.orderPresentation.OrderVisitorShortPresentation

/**
 * Команда оплаты заказа.
 */
class PayForOrderCommand(override var description: String = "Оплатить заказ") : Command<Application> {
    override fun execute(argument: Application) {
        ConsoleOutputHelper.displayOrders(argument.orderStorage.getUserOrders(argument.session.user!!.id), OrderVisitorShortPresentation())
        println()
        var orderToPay: Order? = null
        do {
            val id = ConsoleInputHelper.readIntCheckBackCommand("Введите номер заказа, который хотите оплатить: ", argument.backCommand) ?: return
            orderToPay = argument.orderStorage.getOrder(id)
            if (orderToPay == null) {
                ConsoleOutputHelper.printMessage("Заказа с таким номером нет!", OutputMessageType.Error)
                continue
            }
            if (orderToPay.status == OrderStatus.Paid) {
                ConsoleOutputHelper.printMessage("Заказ уже оплачен")
                orderToPay = null
                continue
            }
            if (orderToPay.status != OrderStatus.Ready) {
                ConsoleOutputHelper.printMessage("Оплатить можно только готовый заказ!", OutputMessageType.Error)
                orderToPay = null
                continue
            }
            // Проверка на то, что пользователь является автором заказа, не добавлнеа,
            // чтобы один посетитель мог заплатить за другого.
        } while (orderToPay == null)

        orderToPay.status = OrderStatus.Paid
        ConsoleOutputHelper.printMessage("Заказ оплачен:")
        println(OrderVisitorPresentation().presentOrder(orderToPay))
        println()
        ConsoleInputHelper.readEnterPress()
    }
}