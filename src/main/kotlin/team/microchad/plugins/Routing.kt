package team.microchad.plugins

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import team.microchad.client.JiraClient
import team.microchad.client.MmClient
import team.microchad.dto.mm.IncomingMsg
import team.microchad.dto.mm.OutgoingMsg
import team.microchad.dto.mm.dialog.Response
import team.microchad.dto.mm.dialog.submissions.SchedulerSubmission
import team.microchad.dto.mm.dialog.submissions.SelectionSubmission
import team.microchad.dto.mm.dialog.submissions.StatisticsSubmission
import team.microchad.dto.mm.slash.*
import team.microchad.model.entities.ProjectsMap
import team.microchad.model.repositories.ProjectMapRepository
import team.microchad.service.*


fun Application.configureRouting() {
    val mmClient: MmClient by inject()
    val jiraClient: JiraClient by inject()
    val userService: UserService by inject()
    val projectMapRepository: ProjectMapRepository by inject()
    routing {

        get("/") {
            call.respondText("Hello, world!")
        }
        //TODO: refactor this
        post("/") {
            call.respond(
                SlashResponse(
                    attachments = arrayOf(
                        Attachment(
                            fallback = "Select your option", color = "#00ff00", actions = arrayOf(
                                Action(
                                    "register", "Bind user to Jira", Integration(
                                        "${Secrets.botHost}/register_dialog"
                                    )
                                ), Action(
                                    "stats", "Get statistics", Integration(
                                        "${Secrets.botHost}/statistics_dialog"
                                    )
                                ), Action(
                                    "scheduler", "Scheduler settings", Integration(
                                        "${Secrets.botHost}/scheduler_dialog"
                                    )
                                )
                            )
                        ),
                    )
                )
            )

        }

        post("/register_dialog") {
            val incomingMsg = call.receive<IncomingMsg>()
            val users = jiraClient.getUsers()
            val dialog = createRegisterJiraDialog(incomingMsg.triggerId, users)
            val response = mmClient.openDialog(dialog)
            println(response)
            call.respond(ActionResponse("Continue registration in dialog window"))
        }
        post("/scheduler_dialog") {
            val projects = jiraClient.getProjects()
            val incomingMsg = call.receive<IncomingMsg>()
            val dialog = createSchedulerDialog(incomingMsg.triggerId, projects)
            mmClient.openDialog(dialog)
            call.respond(ActionResponse("Continue setting in dialog window"))
        }

        post("/statistics_dialog") {
            //TODO: Change endpoint after testing, move code into another class or fun
            val statuses = jiraClient.getStatuses()
            val projects = jiraClient.getProjects()
            val incomingMsg = call.receive<IncomingMsg>()
            val dialog = createStatisticsDialog(incomingMsg.triggerId, statuses, projects)
            mmClient.openDialog(dialog)
            call.respond(ActionResponse("Continue in dialog window"))
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

        post("/scheduler") {
            val incoming = call.receive<Response<SchedulerSubmission>>()
            val project = incoming.submission?.selectProject
            val radioScheduler = incoming.submission?.radioScheduler
            val channelId = mmClient.createDirectChannel(incoming.userId)
            projectMapRepository
            val outgoingMessage = OutgoingMsg(channelId, "$project, $radioScheduler, ${incoming.channelId}")
            mmClient.sendToDirectChannel(outgoingMessage)
        }

        post("/register_user") {
            val result = call.receive<Response<SelectionSubmission>>()
            if (!result.cancelled) {
                with(result) {
                    userService.registerUser(userId, submission!!.jiraUser)
                }
            }
        }

    }
}
