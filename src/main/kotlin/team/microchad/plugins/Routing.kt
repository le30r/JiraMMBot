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
import team.microchad.dto.mm.dialog.Dialog
import team.microchad.dto.mm.dialog.DialogMessage
import team.microchad.dto.mm.dialog.elements.TextElement
import team.microchad.dto.mm.fromParam
import team.microchad.service.markdown
import java.util.*


fun Application.configureRouting() {
    val mikeBot: MmClient by inject()
    routing {

        get("/") {
            call.respondText("Hello, world!")
        }
        post("/") {
            val statuses = JiraClient().getStatuses()
            val users = JiraClient().getUsers()

            val incomingMsg = fromParam(call.receiveParameters())
            val elements = listOf(TextElement("Display Name","name", "text","text",null,null,null,null,null,"placeholder"))
            val dialog = Dialog(UUID.randomUUID().toString(), "Dialog title", null, elements);
            val dialogMessage = DialogMessage(incomingMsg.trigger_id, "http://localhost:8080/dialog", dialog);
            mikeBot.openDialog(dialogMessage)
//
//            val response = mikeBot.createDirectChannel(incomingMsg)
//            println(response)
//            val directChannel = response.body<DirectChannel>()
//            println(directChannel)
//            val outgoingMsg = OutgoingMsg(directChannel.id, incomingMsg.text)
//            println(
//                mikeBot.sendToDirectChannel(outgoingMsg)
//            )
            call.respondText(markdown { bold { "[Check response](http://10.8.0.1:8065/microchad/messages/@mike-bot)" } })
            //TODO Описание работы
            // parse jql from incoming msg using msgController. return Jql
            // send jql using JiraClient. If all OK get JiraResponse and create outgoing msg
            // else if some problems create msg with problem description
            // send msg using mmClient

        }

        post("/dialog") {
            println(call.receiveText())
        }

    }
}
