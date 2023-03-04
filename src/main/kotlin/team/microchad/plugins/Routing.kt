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
import team.microchad.dto.mm.dialog.Response
import team.microchad.dto.mm.dialog.elements.Option
import team.microchad.dto.mm.dialog.elements.SelectElement
import team.microchad.dto.mm.dialog.submissions.SelectionSubmission
import team.microchad.dto.mm.fromParam
import team.microchad.service.UserService
import team.microchad.service.markdown

import java.util.*


fun Application.configureRouting() {
    val mikeBot: MmClient by inject()
    val jiraClient: JiraClient by inject()
    val userService: UserService by inject()
    routing {

        get("/") {
            call.respondText("Hello, world!")
        }

        post("/") {
            //TODO: Change endpoint after testing, move code into another class or fun
            val statuses = jiraClient.getStatuses()
            val users = jiraClient.getUsers()
            val incomingMsg = fromParam(call.receiveParameters())
            val options = users.map { Option(it.name, it.key) }
            val selectElement = SelectElement("Select your Jira user", "selection", options = options)

            val dialog = Dialog(
                UUID.randomUUID().toString(),
                "Select Jira User",
                null,
                listOf(selectElement),
                notifyOnCancel = true
            )

            val dialogMessage = DialogMessage(incomingMsg.triggerId, "${Secrets.botHost}/register_user", dialog)
            mikeBot.openDialog(dialogMessage)
            call.respondText(
                markdown {
                    bold {
                        "Continue registration in dialog window"
                    }
                }
            )
        }

        post("/dialog") {
            println(call.receiveText())
        }

        post("/register_user") {
            //println(call.receiveText())
            val result = call.receive<Response<SelectionSubmission>>()
            if (!result.cancelled) {
                with(result) {
                    userService.registerUser(userId, submission!!.selection)
                }
            }
        }

    }
}
