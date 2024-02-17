package org.example.states

import org.example.Application
import org.example.utils.AuthChecker

/**
 * Состояние приложения, при котором отображается главное меню.
 */
class MainMenuState(application: Application, previousState: ApplicationState): ApplicationState(application, previousState) {
    override fun process() {
        AuthChecker.requireLogin(application)


    }
}