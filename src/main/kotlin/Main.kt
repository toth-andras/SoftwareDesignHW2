package org.example
import entities.cooking.CookingTask
import org.example.entities.orders.OrderStatus
import org.example.factories.application.ApplicationMemberFactory
import org.example.factories.application.JsonApplicationMemberFactory
import org.example.states.InitialState

fun main() {
    val applicationMemberFactory: ApplicationMemberFactory = JsonApplicationMemberFactory()

    val application = Application(applicationMemberFactory)
    application.state = InitialState(application)
    application.initialize()

    while(!application.exitRequired) {
        application.process()
    }

    application.destruct()
}