package team.microchad.plugins

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import team.microchad.client.JiraClient
import team.microchad.client.MmClient
import team.microchad.dto.mm.OutgoingMsg
import team.microchad.dto.mm.createMessageFromParam
import team.microchad.dto.mm.dialog.Response
import team.microchad.dto.mm.dialog.submissions.SchedulerSubmission
import team.microchad.dto.mm.dialog.submissions.SelectionSubmission
import team.microchad.dto.mm.dialog.submissions.StatisticsSubmission
import team.microchad.service.*


fun Application.configureRouting() {
    val mmClient: MmClient by inject()
    val jiraClient: JiraClient by inject()
    val userService: UserService by inject()
    routing {

        get("/") {
            call.respondText("Hello, world!")
        }

        post("/") {
            val statuses = jiraClient.getStatuses()
            val incomingMsg = createMessageFromParam(call.receiveParameters())
            val dialog = createSchedulerDialog(incomingMsg.triggerId, statuses)
            mmClient.openDialog(dialog)
            call.respondText(
                markdown {
                    bold {
                        "Continue in dialog window"
                    }
                }
            )
        }

        post("/scheduler") {
            val incoming = call.receive<Response<SchedulerSubmission>>()
            val status = incoming.submission?.selectStatus
            val dayOfWeek = incoming.submission?.dayOfWeek
            val hour = incoming.submission?.hour
            val minutes = incoming.submission?.cronMinutes
            val channelId = mmClient.createDirectChannel(incoming.userId)
            val outgoingMessage = OutgoingMsg(channelId, "$status, $dayOfWeek, $hour, $minutes")
            mmClient.sendToDirectChannel(outgoingMessage)
        }

        post("/statistics_dialog") {
            //TODO: Change endpoint after testing, move code into another class or fun
            val statuses = jiraClient.getStatuses()
            val projects = jiraClient.getProjects()
            val incomingMsg = createMessageFromParam(call.receiveParameters())
            val dialog = createStatisticsDialog(incomingMsg.triggerId, statuses, projects)
            mmClient.openDialog(dialog)
            call.respondText(
                markdown {
                    bold {
                        "Continue in dialog window"
                    }
                }
            )
        }

        post("/statistics") {
            val incoming = call.receive<Response<StatisticsSubmission>>()
            val status = incoming.submission?.selectStatus
            val project = incoming.submission?.selectProject
            val userCheckbox = incoming.submission?.userCheckbox
            val userSelect = incoming.submission?.userSelect

            val jqlRequest = if (userCheckbox == "true") {
                val jiraUser = userService.getJiraUsername(userSelect!!)
                getUserIssuesWithStatus(jiraUser, status ?: "", project ?: "")
            } else {
                getIssuesWithChangedStatusByProject(status ?: "", project ?: "")
            }
            val channelId = mmClient.createDirectChannel(incoming.userId)
            val responseJira = jiraClient.getByJql(jqlRequest)
            val outgoingMessage = getOutgoingMessageForIssues(channelId, responseJira.issues)
            mmClient.sendToDirectChannel(outgoingMessage)
        }

        post("/dialog") {
            println(call.receiveText())
        }

        post("/register_dialog") {
            val incomingMsg = createMessageFromParam(call.receiveParameters())
            val users = jiraClient.getUsers()
            val dialog = createRegisterJiraDialog(incomingMsg.triggerId, users)
            mmClient.openDialog(dialog)
            call.respondText(
                markdown {
                    bold {
                        "Continue registration in dialog window"
                    }
                }
            )
        }

        post("/register_user") {
            //todo: implement adding into DB
            //println(call.receiveText())
            val result = call.receive<Response<SelectionSubmission>>()
            if (!result.cancelled) {
                with(result) {
                    userService.registerUser(userId, submission!!.jiraUser)
                }
            }
        }

    }
}
