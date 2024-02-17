package org.example.commands.menu

import org.example.Application
import org.example.commands.Command
import org.example.states.menu.MenuStatisticsState

/**
 * Переводит приложение в состояние отображения статистики блюд.
 */
class MenuStatisticsCommand(override var description: String = "Статистика блюд") : Command<Application> {
    override fun execute(argument: Application) {
        argument.state = MenuStatisticsState(argument, argument.state)
    }
}