package team.microchad.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.util.reflect.*


fun Application.configureRouting() {

    routing {
        get("/"){

            call.respond("hello")
        }
    }
}
