package org.example.states

import org.example.Application

/**
 * Представляет обобщённый интерфейс состояния приложения.
 */
abstract class ApplicationState(val application: Application, val previousState: ApplicationState? = null) {
    /**
     * Метод, хранящий поведение приложения в данном состоянии.
     */
    abstract fun process()
}