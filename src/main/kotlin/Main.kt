package org.example
import org.example.states.InitialState

fun main() {
    val application = Application()
    application.state = InitialState(application)
    application.userStorage.initialize()
    application.menuStorage.initialize()

    while(!application.exitRequired) {
        application.process()
    }

    application.userStorage.destruct()
    application.menuStorage.destruct()
}