package org.example.states.menu

import org.example.Application
import org.example.states.ApplicationState
import org.example.states.InitialState
import org.example.utils.AuthChecker

/**
 * Состояние приложения, при котором пользователю отображается статистика поо блюдам.
 */
class MenuStatisticsState(application: Application, previousState: ApplicationState?): ApplicationState(application, previousState) {
    override fun process() {
        AuthChecker.requireAdmin(application, InitialState(application))
    }
}