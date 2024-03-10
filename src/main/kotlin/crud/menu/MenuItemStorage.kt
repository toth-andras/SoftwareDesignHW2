package org.example.crud.menu

import org.example.crud.Storage
import org.example.entities.menu.MenuItem

/**
 * Представляет обощенный интерфейс хранилища блюд.
 */
interface MenuItemStorage: Storage {
    /**
     * Создает блюдо с переданными данными и сохраняет его в хранилище.
     * @param name название блюда.
     * @param quantity количество порций блюда.
     * @param price цена за одну порцию блюда.
     * @param timeToCook время преготовления одной порции блюда.
     * @return блюдо, добавленное в хранилище.
     */
    fun createMenuItem(name: String, quantity: Int, price: Int, timeToCook: Int): MenuItem

    /**
     * Добавляет в хранилище уже существующее блюдо, переопределяя его id.
     * @param menuItem блюдо, которое необходимо доаавить в хранилище.
     */
    fun addMenuItem(menuItem: MenuItem)

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

    /**
     * Удаляет из хранилища блюдо с переданным id.
     * @param id id блюда, которое необходимо удалить.
     * @return true, если блюдо с переданным id было удалено и false иначе.
     */
    fun removeMenuItem(id: Int): Boolean
}