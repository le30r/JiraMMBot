package team.microchad.plugins

import io.ktor.client.call.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject
import team.microchad.client.JiraClient
import team.microchad.client.MmClient
import team.microchad.dto.mm.DirectChannel
import team.microchad.dto.mm.OutgoingMsg
import team.microchad.dto.mm.fromParam
import team.microchad.service.markdown


fun Application.configureRouting() {
    val mikeBot: MmClient by inject()
    routing {

        get("/") {
            call.respondText("Hello, world!")
        }
        post("/") {
            val statuses = JiraClient().getStatuses()
            val incomingMsg = fromParam(call.receiveParameters())
            val response = mikeBot.createDirectChat(incomingMsg)
            println(response)
            val directChannel = response.body<DirectChannel>()
            println(directChannel)
            val outgoingMsg = OutgoingMsg(directChannel.id, incomingMsg.text)
            println(
                mikeBot.sendToDirect(outgoingMsg)
            )
            call.respondText(markdown { bold { "[Check response](http://tin-workshop.ddns.net:8065/microchad/messages/@mike-bot)" } })
            //TODO Описание работы
            // parse jql from incoming msg using msgController. return Jql
            // send jql using JiraClient. If all OK get JiraResponse and create outgoing msg
            // else if some problems create msg with problem description
            // send msg using mmClient

        }
    }
}
