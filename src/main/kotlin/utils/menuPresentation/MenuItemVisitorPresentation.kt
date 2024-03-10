package org.example.utils.menuRepresentation

import org.example.entities.menu.MenuItem
import org.example.utils.orderPresentation.MenuItemPresentationStrategy

/**
 * Инкапсулирует стратегию создания строчного отображения блюда для посетителя.
 */
class MenuItemVisitorPresentation: MenuItemPresentationStrategy {
    override fun presentMenuItem(menuItem: MenuItem): String {
        return "${menuItem.id}. ${menuItem.name} ........... ${menuItem.price} \uD83E\uDE99"
    }
}