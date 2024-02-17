package org.example.states.menu

import org.example.Application
import org.example.commands.factories.ManageMenuStateCommandFactory
import org.example.crud.menu.MenuItemStorage
import org.example.crud.menu.MenuItemStorageJson
import org.example.entities.menu.MenuItem
import org.example.states.ApplicationState
import org.example.states.InitialState
import org.example.states.MainMenuState
import org.example.utils.AuthChecker
import org.example.utils.CommandReader
import org.example.utils.ConsoleOutputHelper

/**
 * Состояние приложения, в котором происходит управление меню.
 */
class ManageMenuState(application: Application, previousState: ApplicationState?): ApplicationState(application, previousState) {
    override fun process() {
        AuthChecker.requireAdmin(application, previousState ?: InitialState(application))

        ConsoleOutputHelper.displayMenu(application.menuStorage.getMenuItems(), this::representMenuItem)

        val commandReader = CommandReader<Application>(ManageMenuStateCommandFactory())
        println()
        commandReader.displayCommands()
        commandReader.readAndExecute(application)
    }

    private fun representMenuItem(menuItem: MenuItem): String {
        return "${menuItem.id}. ${menuItem.name}: осталось порций: ${menuItem.quantity}," +
                " цена: ${menuItem.price}, время приготовления: ${menuItem.timeToCook} мин."
    }
}