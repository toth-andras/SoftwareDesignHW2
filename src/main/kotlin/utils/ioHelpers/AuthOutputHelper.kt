package org.example.utils.ioHelpers

import org.example.entities.auth.Session
import org.example.utils.extensions.LocalDateTimeExtension.Companion.toBeautifulString

class AuthOutputHelper {
    companion object{
        /**
         * Отобразить информацию о пользователе, авторизованном в сессии.
         * @param  session сессия.
         */
        fun printSessionUser(session: Session) {
            if (session.user == null){
                return
            }

            val userType = if (session.user!!.isAdmin) "Сотрудник" else "Посетитель"

            println("=========================================================================================")
            println("\uD83D\uDC64 ${session.user!!.login} ($userType), время входа: ${session.dateOfAuthorization!!.toBeautifulString()}")
            println("=========================================================================================")
        }
    }
}