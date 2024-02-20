package org.example.observation

/**
 * Представляет аналог события из C#.
 *
 * Первоначально я хотел использовтаь чистый паттерн Наблюдатель,
 * но он недостаточно гибок: он не рассчитан на слуай, когда наблюдатель наблюдает сразу за несколькими объектами.
 *
 * @param T агрумент, передаваемый событием всем наблюдателям.
 */
class Event<T> {
    private val _observers: MutableList<(T) -> Unit> = mutableListOf()

    /**
     * Вызвать событие.
     */
    operator fun invoke(argument: T) {
        _observers.forEach {it.invoke(argument)}
    }

    /**
     * Добавить переданный метод к методам, которые будут вызваны в случае вызова события.
     * @param observerMethod метод.
     */
    operator fun plusAssign(observerMethod: (T) -> Unit) {
        _observers.add(observerMethod)
    }

    /**
     * Удалить переданный метод из методов, которые будут вызваны в случае вызова события.
     * @param observerMethod метод.
     */
    operator fun minusAssign(observerMethod: (T) -> Unit) {
        _observers.remove(observerMethod)
    }
}