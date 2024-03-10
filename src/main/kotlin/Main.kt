package org.example

import entities.cooking.CookingTask
import org.example.crud.auth.UserLoginCollisionException
import org.example.entities.menu.NotEnoughPortionsException
import org.example.entities.orders.OrderModificationProhibitedException
import org.example.entities.orders.OrderStatus
import org.example.factories.application.ApplicationMemberFactory
import org.example.factories.application.JsonApplicationMemberFactory
import org.example.states.InitialState
import org.example.utils.ioHelpers.ConsoleOutputHelper
import org.example.utils.ioHelpers.OutputMessageType

fun main() {
    val applicationMemberFactory: ApplicationMemberFactory = JsonApplicationMemberFactory()

    val application = Application(applicationMemberFactory)
    application.state = InitialState(application)
    application.initialize()

    try {
        while (!application.exitRequired) {
            application.process()
        }
    } catch (e: NotEnoughPortionsException) {
        ConsoleOutputHelper.printMessage(
            "Во время работы с программой возникло исключение (ошибка при работе с меню). Перезапустите программу!",
            OutputMessageType.Error
        )
    } catch (e: OrderModificationProhibitedException) {
        ConsoleOutputHelper.printMessage(
            "Во время работы с программой возникло исключение (ошибка при работе с заказами). Перезапустите программу!",
            OutputMessageType.Error
        )
    } catch (e: UserLoginCollisionException) {
        ConsoleOutputHelper.printMessage(
            "Во время работы с программой возникло исключение (ошибка при работе с логинами пользователей)." +
                    " Перезапустите программу!",
            OutputMessageType.Error
        )
    } catch (e: Exception) {
        ConsoleOutputHelper.printMessage(
            "Во время работы возникла критическая неисправность. Перезапустите программу!",
            OutputMessageType.Error
        )
    }

    application.destruct()
}