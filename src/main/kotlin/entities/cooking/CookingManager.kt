package entities.cooking

import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

/**
 * Осуществляет контроль над всеми заказами, требующими приготовления.
 * @param workersNum — количетсво обработчиков заказов, работающих одновременно.
 */
class CookingManager(val workersNum: Int) {
    private val _lock: Lock = ReentrantLock()
    private val _tasks: PriorityQueue<CookingTask> = PriorityQueue(CookingTaskComparator())
    private val _workers: MutableList<CookingWorker> = mutableListOf()

    init {
        require(workersNum > 0) {"Количетсво обработчиков заказов должно быть положительным."}
        for (i in 1..workersNum) {
            _workers.add(CookingWorker(this))
        }
    }

    /**
     * Добавляет задачу в список ожидания.
     * @param task задача.
     */
    fun addTask(task: CookingTask) {
        _lock.lock()
        _tasks.add(task)
        _lock.unlock()
    }

    /**
     * Извлекает из списка ожидания задачу с наибольшим приоритетом.
     * @return задача или null, если в списке ожидания нет задач.
     */
    fun getTask(): CookingTask? {
        _lock.lock()
        if (_tasks.isEmpty()) {
            _lock.unlock()
            return null
        }
        val taskToReturn = _tasks.remove()
        _lock.unlock()
        return taskToReturn
    }

    /**
     * Запускает работу над заказами.
     */
    fun startWorking() {
        val executor = Executors.newFixedThreadPool(workersNum)

        for (i in 1..workersNum) {
            executor.submit(Callable {
                _workers[i].startWorking()
            })
        }
        executor.shutdown()
    }

    /**
     * Останавливает работу над заказами.
     */
    fun stopWorking() {
        for (i in 1..<workersNum) {
            _workers[i].stopWorking()
        }
    }
}