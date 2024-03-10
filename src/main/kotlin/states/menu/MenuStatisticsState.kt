package org.example.states.menu

import org.example.Application
import org.example.states.ApplicationState
import org.example.states.InitialState
import org.example.utils.AuthChecker
import org.example.utils.StringColorizer
import org.example.utils.extensions.LocalDateTimeExtension.Companion.toBeautifulString
import org.example.utils.ioHelpers.ConsoleInputHelper

/**
 * Состояние приложения, при котором пользователю отображается статистика поо блюдам.
 */
class MenuStatisticsState(application: Application, previousState: ApplicationState?): ApplicationState(application, previousState) {
    override fun process() {
        AuthChecker.requireAdmin(application, InitialState(application))

        var totalProfit = 0
        var totalPurchases = 0

        println("Статистика по блюдам: ")
        for (menuItem in application.menuStorage.getMenuItems()) {
            println(StringColorizer.toYellow("— ${menuItem.id} ${menuItem.name} —"))
            val statistics = application.statisticsManager.statisticsStorage.getStatistics(menuItem.id)
            if (statistics == null) {
                println("\t Нет данных.")
                continue
            }
            println("\t Количетство купленных порций: ${statistics.timesPurchased}")
            println("\t Прибыль от продажи блюда: ${statistics.profit}")
            if (statistics.lastPurchaseDate != null) {
                println("\t Дата и время последнего приобретения: ${statistics.lastPurchaseDate!!.toBeautifulString()}")
            }
            if (statistics.reviews.isNotEmpty()) {
                println("\t Средняя оценка блюда: ${statistics.reviews.map { it.mark }.average()}")
                println("\t Отзывы:")
                statistics.reviews.forEach{println("\t\t$it")}
            }
            println()
            totalProfit += statistics.profit
            totalPurchases += statistics.timesPurchased
        }
        println()
        println("======================================================================================================")
        println("Общая прибыль: $totalProfit, всего блюд продано: $totalPurchases")


        ConsoleInputHelper.readEnterPress()
        application.state = previousState
    }
}