package org.example.commands.navigation

import org.example.Application
import org.example.commands.Command
import org.example.states.menu.MenuStatisticsState

/**
 * Переводит приложение в состояние отображения статистики блюд.
 */
class MenuStatisticsCommand(override var description: String = "Статистика блюд") : Command<Application> {
    override fun execute(argument: Application) {
        // TODO: убрать заглушку
        return
        argument.state = MenuStatisticsState(argument, argument.state)
    }
}