package team.microchad.plugins

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

import org.koin.ktor.ext.inject

import team.microchad.client.JiraClient
import team.microchad.client.MmClient
import team.microchad.controllers.RegistrationController
import team.microchad.controllers.StatisticsController
import team.microchad.dto.mm.IncomingMsg
import team.microchad.dto.mm.OutgoingMsg
import team.microchad.dto.mm.dialog.Response
import team.microchad.dto.mm.dialog.submissions.SchedulerSubmission
import team.microchad.dto.mm.dialog.submissions.SelectionSubmission
import team.microchad.dto.mm.dialog.submissions.StatisticsSubmission
import team.microchad.dto.mm.slash.*
import team.microchad.model.entities.ProjectMap
import team.microchad.model.repositories.ProjectMapRepository
import team.microchad.service.*


fun Application.configureRouting() {
    val mmClient: MmClient by inject()
    val jiraClient: JiraClient by inject()
    val userService: UserService by inject()
    val projectMapRepository: ProjectMapRepository by inject()

    val registrationController: RegistrationController by inject()
    val statisticsController: StatisticsController by inject()

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
            val projects = jiraClient.getProjects()
            val incomingMsg = call.receive<IncomingMsg>()
            val dialog = createSchedulerDialog(incomingMsg.triggerId, projects)
            mmClient.openDialog(dialog)
            call.respond(ActionResponse("Continue setting in dialog window"))
        }

        post("/statistics_dialog") {
            //TODO: Change endpoint after testing, move code into another class or fun
            val incomingMsg = call.receive<IncomingMsg>()
            val actionResponse = statisticsController.openDialog(incomingMsg)
            call.respond(actionResponse)
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
            val project = incoming.submission?.selectProject?:""
            val radioScheduler = incoming.submission?.radioScheduler
            val channelId = incoming.channelId
            val projectMap = ProjectMap(null, project, channelId)
            if (radioScheduler == "on") {
                projectMapRepository.create(projectMap)
            } else {
                //TODO DELETE FROM DB
            }
            val outgoingMessage = OutgoingMsg(channelId, "$project, $radioScheduler, ${incoming.channelId}")
            mmClient.sendToDirectChannel(outgoingMessage)
        }

        post("/register_user") {
            val result = call.receive<Response<SelectionSubmission>>()
            val actionResponse = registrationController.registerUser(result)
            call.respond(actionResponse)
        }

    }
}
