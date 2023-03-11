package team.microchad.plugins

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

import org.koin.ktor.ext.inject

import team.microchad.client.JiraClient
import team.microchad.client.MmClient
import team.microchad.controllers.RegistrationController
import team.microchad.controllers.SchedulerController
import team.microchad.controllers.StatisticsController
import team.microchad.dto.jira.Issue
import team.microchad.dto.mm.IncomingMsg
import team.microchad.dto.mm.dialog.Response
import team.microchad.dto.mm.dialog.submissions.CommentSubmission
import team.microchad.dto.mm.dialog.submissions.SchedulerSubmission
import team.microchad.dto.mm.dialog.submissions.SelectionSubmission
import team.microchad.dto.mm.dialog.submissions.StatisticsSubmission
import team.microchad.model.repositories.ProjectMapRepository
import team.microchad.service.*
import team.microchad.utils.toUrlForm
import javax.lang.model.type.TypeVariable


fun Application.configureRouting() {
    val mmClient: MmClient by inject()
    val jiraClient: JiraClient by inject()
    val userService: UserService by inject()
    val projectMapRepository: ProjectMapRepository by inject()

    val registrationController: RegistrationController by inject()
    val statisticsController: StatisticsController by inject()
    val schedulerController: SchedulerController by inject()

    routing {

        get("/") {
            call.respondText("Hello, world!")
        }

        post("/") {
            call.respond(
                createJiraBotMessage()
            )
        }

        post("/register_dialog") {
            val incomingMsg = call.receive<IncomingMsg>()
            val actionResponse = registrationController.openDialog(incomingMsg)
            call.respond(actionResponse)
        }
        post("/scheduler_dialog") {
            val incomingMsg = call.receive<IncomingMsg>()
            val actionResponse = schedulerController.openDialog(incomingMsg)
            call.respond(actionResponse)
        }

        post("/statistics_dialog") {
            //TODO: Change endpoint after testing, move code into another class or fun
            val incomingMsg = call.receive<IncomingMsg>()
            val actionResponse = statisticsController.openDialog(incomingMsg)
            call.respond(actionResponse)
        }

        post("commentIssue_dialog") {
            val result = call.receive<IncomingMsg>()
            val project = projectMapRepository.findById(result.channelId)
            val issues = jiraClient.getByJql(getIssuesByProject(project?.project?:"MMJIR").toUrlForm()).issues
            val test = createCommentIssueDialog(result.triggerId, issues )
            mmClient.openDialog(test)
            call.respond(ActionResponse("Comment in dialog window"))
        }

        post("/statistics") {
            val incoming = call.receive<Response<StatisticsSubmission>>()
            statisticsController.sendStatistics(incoming)
        }

        post("/dialog") {
            println(call.receiveText())
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
            jiraClient.commentIssue(result.submission?.issue?:"", result.submission?.comment?:"")
        }

    }
}

