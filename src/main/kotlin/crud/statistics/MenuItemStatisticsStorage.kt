package org.example.crud.statistics

import org.example.crud.Storage
import org.example.entities.menu.MenuItem
import org.example.entities.statistics.MenuItemStatistics

/**
 * Представляет общий интерфейс всех хранилищ объектов статистики блюд.
 */
interface MenuItemStatisticsStorage: Storage {

    /**
     * Создает новый объект статистики для переданного блюда и добавляет его в хранилище.
     * @param menuItem блюдо.
     * @return созданный объект статистики.
     */
    fun createStatistics(menuItem: MenuItem): MenuItemStatistics

    /**
     * Возвращает объект статистики для блюда с переданным идентификатором.
     * @param menuItemId идентификатор блюда.
     * @return объект статистики для блюда или null, если статистики для блюда с таким идентификатором нет.
     */
    fun getStatistics(menuItemId: Int): MenuItemStatistics?

    /**
     * Удаляет объект статистики для блюда с переданным идентификатором.
     * @param menuItemId идентификатор блюда.
     * @return true, если объект статистики был удален, и false иначе.
     */
    fun removeStatistics(menuItemId: Int): Boolean

    /**
     * Сохранить изменения.
     */
    fun save()
}