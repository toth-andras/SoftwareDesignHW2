package org.example.entities.auth
import kotlinx.serialization.Serializable

/**
 * Хранит информацию о пользователе приложения.
 */
@Serializable
class User private constructor(val id: Int, val login: String, private var _passwordHash: Int, val isAdmin: Boolean) {
    constructor(id: Int, login: String, password: String, isAdmin: Boolean):
            this(id, login, password.hashCode(), isAdmin){}

    init {
        require(login.trim().isNotEmpty()) {"Логин не может быть пустым."}
    }

    /**
     * Проверяет, является ли переданный пароль паролем пользователя.
     * @param password пароль для проверки.
     * @return true, если переданный пароль совпадает с паролем пользователя и false иначе.
     */
    fun checkPassword(password: String): Boolean = password.hashCode() == _passwordHash

    /**
     * Меняет пароль пользователя.
     * @param oldPassword старый пароль пользователя.
     * @param newPassword новый пароль пользователя.
     * @return true, если смена пароля произошла и false иначе.
     */
    fun changePassword(oldPassword: String, newPassword: String): Boolean {
        if (!checkPassword(oldPassword)) {
            return false
        }
        _passwordHash = newPassword.hashCode()
        return true
    }

    override fun toString(): String {
        return "$login (${ if (isAdmin) "(Администратор)" else "Посетитель"})"
    }
}