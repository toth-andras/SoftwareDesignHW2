package org.example

import org.example.states.ApplicationState
import org.example.crud.auth.UserStorage
import org.example.crud.menu.MenuItemStorage
import org.example.crud.orders.OrderStorage
import org.example.crud.orders.OrderStorageJson
import org.example.entities.auth.Session
import entities.cooking.CookingManager
import entities.cooking.CookingTask
import org.example.crud.statistics.MenuItemStatisticsStorageJson
import org.example.entities.orders.OrderStatus
import org.example.entities.statistics.StatisticsManager
import org.example.factories.application.ApplicationMemberFactory
import org.example.factories.commands.OrdersStateAdminCommandFactory
import org.example.utils.ioHelpers.AuthOutputHelper
import org.example.utils.ioHelpers.ConsoleOutputHelper

/**
 * Представляет контектс приложения — здесь хранятся данные,
 * общие для разных его состояний.
 */
class Application (factory: ApplicationMemberFactory) {
    /**
     * Хранит текущего пользователя и время его входа.
     */
    val session = Session()

    /**
     * Состояние приложения.
     */
    var state: ApplicationState? = null

    /**
     * Отображет, запросил ли пользователь выход из приложения.
     */
    var exitRequired: Boolean = false

    /**
     * Хранит команду перехода в предыдущее состояние.
     */
    var backCommand = factory.getBackCommand()

    /**
     * Хранилище со всеми пользователями приложения.
     */
    var userStorage: UserStorage = factory.getUserStorage()

    /**
     * Хранилища со всеми блюдами.
     */
    val menuStorage: MenuItemStorage = factory.getMenuItemStorage()

    /**
     * Хранилище со всеми заказами.
     */
    val orderStorage: OrderStorage = factory.getOrderStorage()

    /**
     * Управляющий обработкой заказов.
     */
    val cookingManager: CookingManager = CookingManager(factory.getCookingTaskWorkersCount())

    /**
     * Управляющий статистикой.
     */
    val statisticsManager: StatisticsManager =  StatisticsManager(factory.getStatisticsStorage())


    /**
     * Цикл работы приложения.
     */
    fun process() {
        ConsoleOutputHelper.clearConsole()
        AuthOutputHelper.printSessionUser(session)
        if (state == null) {
            exitRequired = true;
            return
        }

        state!!.process()
    }

    /**
     * Инициализирует все необходимые данные и запускает обработчики заказов.
     */
    fun initialize() {
        // Инициализация хранилищ.
        userStorage.initialize()
        menuStorage.initialize()
        orderStorage.initialize()
        statisticsManager.statisticsStorage.initialize()

        // Инициализация и запуск управляющего заказами.
        orderStorage.getOrders()
            .filter { it.status == OrderStatus.Created || it.status == OrderStatus.OnCook }
            .forEach{cookingManager.addTask(CookingTask(it))}
        orderStorage.orderCreated += {cookingManager.addTask(CookingTask(it))}

        cookingManager.startWorking()
    }

    /**
     * Отрабатывает завершение работы со всеми хранилищами и завершает работу обработчиков заказов.
     */
    fun destruct() {
        cookingManager.stopWorking()
        cookingManager.stopWorking()
        userStorage.destruct()
        menuStorage.destruct()
        orderStorage.destruct()
        statisticsManager.statisticsStorage.destruct()
    }
}