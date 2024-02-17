package org.example.crud.auth

import org.example.entities.auth.User

/**
 * Представляет обощенный интерфейс для хранилища пользователей приложения.
 */
interface UserStorage {
    /**
     * Инициализирует хранилище.
     */
    fun initialize()

    /**
     * Содержит логику завершения работы с хранилищем.
     */
    fun destruct()

    /**
     * Проверяет, существует ли пользователь с этим логином в системе.
     * @param login логин для проверки.
     * @return true, если пользователь с таким логином есть, и false в противном случае.
     */
    fun loginExists(login: String): Boolean

    /**
     * Создает пользователя с переданными данными и сохраняет его в хранилище.
     * @param login логин пользователя.
     * @param password пароль пользователя.
     * @param isAdmin является ли пользователь администратором.
     * @return объект пользователя, добавленный в хранилище.
     * @exception UserLoginCollisionException пользователь с такми логином уже сууществует.
     * @exception IllegalArgumentException попытка создать пользователя с пустым логином.
     */
    fun createUser(login: String, password: String, isAdmin: Boolean): User

    /**
     * Получает из хранилища объект пользователя для переданного логина.
     * @param login логин пользователя.
     * @return объект пользователя или null, если пользователя с таким логином нет в хранилище.
     */
    fun getUser(login: String): User?

    /**
     * Получает из хранилища объект пользователя для переданного id.
     * @param id логин пользователя.
     * @return объект пользователя или null, если пользователя с таким id нет в хранилище.
     */
    fun getUser(id: Int): User?

    /**
     * Удаляет пользователя с переданным логином из хранилища.
     * @param id логин пользователя.
     * @return true, если пользователь с переданным id был удален и false иначе.
     */
    fun removeUser(id: Int): Boolean
}