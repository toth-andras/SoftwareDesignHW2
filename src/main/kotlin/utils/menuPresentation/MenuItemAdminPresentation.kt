package org.example.utils.menuRepresentation

import org.example.entities.menu.MenuItem
import org.example.utils.orderPresentation.MenuItemPresentationStrategy

/**
 * Инкапсулирует стратегию создания строчного отображения блюда для администратора.
 */
class MenuItemAdminPresentation: MenuItemPresentationStrategy {
    override fun presentMenuItem(menuItem: MenuItem): String {
        return "${menuItem.id}. ${menuItem.name}: осталось порций: ${menuItem.quantity}," +
                " цена: ${menuItem.price}, время приготовления: ${menuItem.timeToCook} мин."
    }
}