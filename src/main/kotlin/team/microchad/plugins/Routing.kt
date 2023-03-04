package team.microchad.plugins

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import team.microchad.client.JiraClient
import team.microchad.client.MmClient
import team.microchad.dto.mm.dialog.Dialog
import team.microchad.dto.mm.dialog.DialogMessage
import team.microchad.dto.mm.dialog.elements.Option
import team.microchad.dto.mm.dialog.elements.SelectElement
import team.microchad.dto.mm.fromParam
import java.util.*


fun Application.configureRouting() {
    val mikeBot: MmClient by inject()
    val jiraClient: JiraClient by inject()
    routing {

        get("/") {
            call.respondText("Hello, world!")
        }
        post("/") {
            val statuses = jiraClient.getStatuses()
            val users = jiraClient.getUsers()
            val incomingMsg = fromParam(call.receiveParameters())
            val options = users.map { Option(it.name, it.key) }
            val selectElement = SelectElement("jira user", "jira_user_select")
            selectElement.options = options

            val dialog = Dialog(UUID.randomUUID().toString(), "Select Jira User", null, listOf(selectElement))
            val dialogMessage = DialogMessage(incomingMsg.trigger_id, "${Secrets.botHost}/dialog", dialog)
            mikeBot.openDialog(dialogMessage)
            call.respond(dialogMessage)
//            call.respondText(
//                markdown {
//                    bold {
//                        "[Check response](http://10.8.0.1:8065/microchad/messages/@mike-bot)"
//                    }
//                }
//            )
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
