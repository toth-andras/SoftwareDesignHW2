package org.example.utils

import org.example.entities.menu.MenuItem
import org.example.entities.orders.Order
import org.example.utils.orderPresentation.MenuItemPresentationStrategy
import org.example.utils.orderPresentation.OrderAdminPresentation
import org.example.utils.orderPresentation.OrderVisitorPresentation
import org.example.utils.orderRepresentation.OrderPresentationStrategy


/**
 * Представляет функционал для вывода информации на консоль.
 */
class ConsoleOutputHelper {
    companion object {
        /**
         * Вывести на экран сообщение об ошибке.
         * @param message сообщение об ошибке.
         */
        fun printMessage(message: String, messageType: OutputMessageType = OutputMessageType.Info) {
            val color: String = when (messageType) {
                OutputMessageType.Error -> "\u001b[31m"
                OutputMessageType.Warning -> "\u001b[33m"
                OutputMessageType.Success -> "\u001B[32m"
                OutputMessageType.Info -> "\u001B[34m"
                else -> "\u001B[37m"
            }
            val reset = "\u001b[0m"

            println(color + message + reset)
        }

        /**
         * Очистить консоль.
         * К сожалению, терминал IntelliJ не поддерживает очистку с помощью cls,
         * поэтому приходится просто сдвигать вниз.
         */
        fun clearConsole() {
            println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n")
        }

        /**
         * Отображает на экран содержимое меню.
         * @param menu меню.
         * @param menuItemPresenter генератор строчного представления одного блюда.
         */
        fun displayMenu(menu: Iterable<MenuItem>, menuItemPresenter: MenuItemPresentationStrategy) {
            println("================================  \uD83C\uDF7D\uFE0F Меню ================================")
            if (!menu.any()) {
                println("В меню не добавлено ни одно блюдо")
                return
            }
            menu.forEach{ "||" + println(menuItemPresenter.presentMenuItem(it)) + "||"}
            println("==========================================================================================")
        }

        /**
         * Отображает на экран переданные заказы.
         * @param orders заказы.
         * @param orderPresenter генератор строчного представления одного заказа.
         * @param isAdmin является ли пользователь, для которого отображаются заказы,
         * администратором (необходимо для генерации текста заголовка).
         */
        fun displayOrders(orders: Iterable<Order>, orderPresenter: OrderPresentationStrategy, isAdmin: Boolean = false) {
            println("\n\n\n")
            println(if (isAdmin) "Заказы" else "Мои заказы")
            println("=========================================================================================")
            val ordersToShow = orders.sortedBy { it.date }.asReversed()
            ordersToShow.forEach{ println(orderPresenter.presentOrder(it)); println() }

            println()
            ConsoleInputHelper.readEnterPress()
        }
    }
}

/**
 * Виды специальных сообщений пользователю.
 */
enum class OutputMessageType {Error, Warning, Success, Info}