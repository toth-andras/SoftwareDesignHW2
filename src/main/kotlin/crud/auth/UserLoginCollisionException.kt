package org.example.crud.auth

/**
 * Вызывается при попытке добавить в хранилище пользоватлея с логином,
 * который уже закреплен за одним из пользователей в хранилище.
 */
class UserLoginCollisionException(collisionLogin: String):
    IllegalStateException("Данный логин ($collisionLogin) уже занят.") {}