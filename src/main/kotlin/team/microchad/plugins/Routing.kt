package team.microchad.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import team.microchad.models.routes.customerRouting

fun Application.configureRouting() {
    routing {
       customerRouting()
    }
}
