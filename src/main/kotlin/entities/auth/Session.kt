package org.example.entities.auth

import kotlinx.datetime.*
import org.example.utils.extensions.LocalDateTimeExtension.Companion.now

/**
 * Хранит информацию об авторизованном в данный момент пользователе (при наличии) и дате авторизации.
 */
class Session {
    private var _user: User? = null
    private var _dateOfAuthorization: LocalDateTime? = null

    /**
     * Текущий авторизованный пользователь или null, если ни один пользователь не авторизован.
     */
    var user: User?
        get() = _user
        private set(value) {
            _user = value
        }

    /**
     * Дата авторизации текущего пользователя или null, если ни один пользователь не авторизован.
     */
    var dateOfAuthorization: LocalDateTime?
        get() = _dateOfAuthorization
        private set(value) {
            _dateOfAuthorization = value
        }

    /**
     * Авторизовать пользователя.
     * @param userToAuthorize пользователь, которого необходимо авторизовать.
     * @param password пароль пользователя, которого необходимо авторизовать.
     * @return true, если авторизация пользователя произведена и false иначе.
     */
    fun authorizeUser(userToAuthorize: User, password: String): Boolean {
        if (!userToAuthorize.checkPassword(password)) {
            return false
        }
        user = userToAuthorize
        dateOfAuthorization = LocalDateTime.now()
        return true
    }

    /**
     * Если какой-либо пользователь авторизован, исключает его из сессии.
     */
    fun deAuthorizeUser(){
        user = null
        dateOfAuthorization = null
    }
}