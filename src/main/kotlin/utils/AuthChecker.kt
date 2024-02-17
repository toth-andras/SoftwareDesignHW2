package org.example.utils

import org.example.Application
import org.example.states.ApplicationState
import org.example.states.InitialState

/**
 * Представляет механизм защиты состояний от несанкционированного доступа.
 */
class AuthChecker {
    companion object {
        /**
         * Проверяет, что в сессии переданного приложения есть авторизованный пользователь. Если в сессии нет
         * авторизованного пользователя, доступ к состоянию не резращается, а приложение
         * переводится в переданное состояние.
         * @param application приложение, сессию которого необходимо проверить.
         * @param redirectState состояние, в которое будет переведено приложение, если в его сессии не будет
         * авторизованного пользователя.
         */
        fun requireLogin(application: Application, redirectState: ApplicationState = InitialState(application)) {
            if (application.session.user == null) {
                ConsoleOutputHelper.printMessage("Необходима авторизация!", OutputMessageType.Error)
                application.state = redirectState
                ConsoleInputHelper.readEnterPress()
            }
        }

        /**
         * Проверяет, что в сессии переданного приложения есть авторизованный администратор. Если требование
         * не выполнено, доступ к состоянию не резращается, а приложение переводится в переданное состояние.
         * @param application приложение, сессию которого необходимо проверить.
         * @param redirectState состояние, в которое будет переведено приложение, если в его сессии не будет
         * авторизованного администратора.
         */
        fun requireAdmin(application: Application, redirectState: ApplicationState = InitialState(application)) {
            requireLogin(application, redirectState)

            if (!application.session.user!!.isAdmin) {
                ConsoleOutputHelper.printMessage("Недостаточно пользовательских прав!", OutputMessageType.Error)
                application.state = redirectState
                ConsoleInputHelper.readEnterPress()
            }
        }
    }
}