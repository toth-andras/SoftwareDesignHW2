package org.example.crud

/**
 * Представялет обощенный интерфей всех хранилищ.
 */
interface Storage {
    /**
     * Инициализирует хранилище.
     */
    fun initialize()

    /**
     * Содержит логику завершения работы с хранилищем.
     */
    fun destruct()
}