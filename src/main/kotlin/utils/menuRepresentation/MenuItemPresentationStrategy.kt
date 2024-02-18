package org.example.utils.orderPresentation

import org.example.entities.menu.MenuItem

/**
 * Представляет обобщённый интерфейс объекта, инкапсулирующего
 * одну из стратегий отображения блюд пользователю.
 */
interface MenuItemPresentationStrategy {
    /**
     * Возвращает представление блюда в виде строки.
     * @param menuItem блюдо.
     * @return представление блюда в виде строки, созданное по определенной стратегии.
     */
    fun presentMenuItem(menuItem: MenuItem): String
}