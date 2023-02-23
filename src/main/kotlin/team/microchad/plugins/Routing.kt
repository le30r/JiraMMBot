package team.microchad.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject
import team.microchad.client.MmClient
import team.microchad.controllers.MessageController
import team.microchad.dto.mm.IncomingMsg


fun Application.configureRouting() {
    val mikeBot: MmClient by inject()
    routing {

        get("/") {
            call.respondText("Hello, world!")
        }
        post("/") {
            var incomingMsg = call.receive<IncomingMsg>()

            //TODO Описание работы
            // parse jql from incoming msg using msgController. return Jql
            // send jql using JiraClient. If all OK get JiraResponse and create outgoing msg
            // else if some problems create msg with problem description
            // send msg using mmClient

            MessageController().chooseCommand(incomingMsg)

        }
    }
}
