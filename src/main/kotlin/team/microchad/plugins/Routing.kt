package team.microchad.plugins

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

import org.koin.ktor.ext.inject

import team.microchad.client.JiraClient
import team.microchad.controllers.IssueController
import team.microchad.controllers.RegistrationController
import team.microchad.controllers.SchedulerController
import team.microchad.controllers.StatisticsController
import team.microchad.dto.mm.IncomingMsg
import team.microchad.dto.mm.dialog.Response
import team.microchad.dto.mm.dialog.submissions.*
import team.microchad.service.createJiraBotMessage


fun Application.configureRouting() {
    val jiraClient: JiraClient by inject()

    val registrationController: RegistrationController by inject()
    val statisticsController: StatisticsController by inject()
    val schedulerController: SchedulerController by inject()
    val issueController: IssueController by inject()

    routing {

        get("/") {
            call.respondText("Hello, world!")
        }

        post("/") {
            call.respond(
                createJiraBotMessage()
            )
        }

        post("register-project_dialog") {
            val incomingMsg = call.receive<IncomingMsg>()
            val actionResponse = registrationController.openProjectDialog(incomingMsg)
            call.respond(actionResponse)
        }

        post("register-project") {
            val incomingMsg = call.receive<Response<ProjectRegistrationSubmission>>()
            registrationController.registerProject(incomingMsg)
        }

        post("/register_dialog") {
            val incomingMsg = call.receive<IncomingMsg>()
            val actionResponse = registrationController.openUserDialog(incomingMsg)
            call.respond(actionResponse)
        }
        post("/scheduler_dialog") {
            val incomingMsg = call.receive<IncomingMsg>()
            val actionResponse = schedulerController.openDialog(incomingMsg)
            call.respond(actionResponse)
        }

        post("/statistics_dialog") {
            val incoming = call.receive<IncomingMsg>()
            val actionResponse = statisticsController.openDialog(incoming)
            call.respond(actionResponse)
        }

        post("/issue_dialog") {
            val incoming = call.receive<IncomingMsg>()
            val actionResponse = issueController.openProjectDialog(incoming)
            call.respond(actionResponse)
        }

        post("/comment_dialog") {
            val incoming = call.receive<IncomingMsg>()
            call.respond(issueController.commentIssueDialog(incoming))
        }

        post("/statistics") {
            val incoming = call.receive<Response<StatisticsSubmission>>()
            statisticsController.sendStatistics(incoming)
        }

        post("/scheduler") {
            val incoming = call.receive<Response<SchedulerSubmission>>()
            schedulerController.configureScheduler(incoming)
        }

        post("/register_user") {
            val result = call.receive<Response<SelectionSubmission>>()
            val actionResponse = registrationController.registerUser(result)
            call.respond(actionResponse)
        }

        post("/comment") {
            val result = call.receive<Response<CommentSubmission>>()
            jiraClient.commentIssue(result.submission?.issue ?: "", result.submission?.comment ?: "")
        }

    }
}

