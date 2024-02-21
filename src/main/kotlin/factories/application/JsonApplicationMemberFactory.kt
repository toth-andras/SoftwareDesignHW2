package org.example.factories.application

import org.example.crud.auth.UserStorage
import org.example.crud.auth.UserStorageJson
import org.example.crud.menu.MenuItemStorage
import org.example.crud.menu.MenuItemStorageJson
import org.example.crud.orders.OrderStorage
import org.example.crud.orders.OrderStorageJson
import org.example.crud.statistics.MenuItemStatisticsStorage
import org.example.crud.statistics.MenuItemStatisticsStorageJson
import org.example.utils.PropertiesAcceser

/**
 * Инициализиурет члены класса Application, задавая в качестве всех хранилищ Json хранилища.
 */
class JsonApplicationMemberFactory: ApplicationMemberFactory {
    override fun getUserStorage(): UserStorage {
        return UserStorageJson(PropertiesAcceser.get("userStoragePath"))
    }

    override fun getMenuItemStorage(): MenuItemStorage {
        return MenuItemStorageJson(PropertiesAcceser.get("menuStoragePath"))
    }

    override fun getOrderStorage(): OrderStorage {
        return OrderStorageJson(PropertiesAcceser.get("orderStoragePath"))
    }

    override fun getStatisticsStorage(): MenuItemStatisticsStorage {
        return MenuItemStatisticsStorageJson(PropertiesAcceser.get("statisticsStoragePath"))
    }

    override fun getCookingTaskWorkersCount(): Int {
        return PropertiesAcceser.get("cookingWorkersCount").toInt()
    }

    override fun getBackCommand(): String {
        return PropertiesAcceser.get("backCommand")
    }
}