package org.example.commands.orders

import org.example.Application
import org.example.commands.Command
import org.example.entities.orders.Order
import org.example.entities.orders.OrderStatus
import org.example.entities.statistics.Review
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

        val orderToPay = readOrder(argument) ?: return
        orderToPay.status = OrderStatus.Paid
        ConsoleOutputHelper.printMessage("Заказ оплачен:")
        println(OrderVisitorPresentation().presentOrder(orderToPay))
        println()
        ConsoleInputHelper.readEnterPress()

        print("Хотели бы Вы оставить на блюда отзыв (Y/N): ")
        if (readln() != "Y") return

        if (readReview(orderToPay, argument)) {
            ConsoleOutputHelper.printMessage("Отзыв(ы) сохранены", OutputMessageType.Success)
            ConsoleInputHelper.readEnterPress()
        }
    }

    private fun readOrder(argument: Application): Order? {
        ConsoleOutputHelper.displayOrders(argument.orderStorage.getUserOrders(argument.session.user!!.id), OrderVisitorShortPresentation())
        println()
        var orderToPay: Order? = null
        do {
            val id = ConsoleInputHelper.readIntCheckBackCommand("Введите номер заказа, который хотите оплатить: ", argument.backCommand) ?: return null
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

        return orderToPay
    }

    private fun readReview(order: Order, argument: Application): Boolean {
        val outputSet: MutableSet<String> = mutableSetOf()
        order.menuItems.forEach{outputSet.add("${it.id}. ${it.name}\n")}
        outputSet.forEach{ print(it) }

        var exit = false
        do {
            val itemId = ConsoleInputHelper.readIntCheckBackCommand("Введите номер блюда, на которое хотели" +
                    " бы оставить отзыв, или -1, чтобы завершить составление отзыва: ", argument.backCommand) ?: return false

            if (itemId == -1) {
                exit = true
                continue
            }

            val itemToReview = order.menuItems.firstOrNull{it.id == itemId}
            if (itemToReview == null){
                ConsoleOutputHelper.printMessage("Блюда с таким номером нет в заказе!", OutputMessageType.Error)
                continue
            }

            val reviewSource = readMarkAndComment(argument) ?: return false
            argument.statisticsManager.reviewAdded(itemToReview, argument.session.user!!.login, reviewSource.first, reviewSource.second)
        } while (!exit)

        return true
    }

    private fun readMarkAndComment(argument: Application): Pair<Int, String>? {
        var mark: Int? = null
        do {
            mark = ConsoleInputHelper.readIntCheckBackCommand("Введите оценку (число от ${Review.minimalMark} до ${Review.maxMark}): ", argument.backCommand) ?: return null

            if (mark < Review.minimalMark || mark > Review.maxMark) {
                ConsoleOutputHelper.printMessage("Оценка не может быть меньше ${Review.minimalMark} и больше ${Review.maxMark}!", OutputMessageType.Error)
                mark = null
            }
        } while (mark == null)

        print("Введите комментарий к отзыву: ")
        val comment = readln()

        return if (comment == argument.backCommand) null else Pair(mark, comment)
    }
}