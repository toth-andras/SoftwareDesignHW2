package org.example.crud.menu

import org.example.crud.Storage
import org.example.entities.menu.MenuItem

/**
 * Представляет обощенный интерфейс хранилища блюд.
 */
interface MenuItemStorage: Storage {
    /**
     * Получает из хранилища все имеющиеся блюда.
     * @return коллекцию блюд из хранилища.
     */
    fun getMenuItems(): Iterable<MenuItem>

    /**
     * Получает из хранилища блюдо.
     * @param id идентификатор блюда, которое надо получить.
     * @return блюдо с переданным id или null, если блюда c переданным id в хранилище нет.
     */
    fun getMenuItem(id: Int): MenuItem?

}