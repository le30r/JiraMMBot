package team.microchad.controllers

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import team.microchad.client.JiraClient
import team.microchad.client.MmClient
import team.microchad.dto.mm.IncomingMsg
import team.microchad.dto.mm.OutgoingMsg
import team.microchad.dto.mm.dialog.Response
import team.microchad.dto.mm.dialog.submissions.SchedulerSubmission
import team.microchad.dto.mm.slash.ActionResponse
import team.microchad.model.entities.ProjectMap
import team.microchad.model.repositories.ProjectMapRepository
import team.microchad.service.UserService
import team.microchad.service.createSchedulerDialog

class SchedulerController: KoinComponent {

    private val mmClient: MmClient by inject()
    private val jiraClient: JiraClient by inject()
    val userService: UserService by inject()
    val projectMapRepository: ProjectMapRepository by inject()

    suspend fun openDialog(incomingMsg: IncomingMsg): ActionResponse {
        val projects = jiraClient.getProjects()
        val dialog = createSchedulerDialog(incomingMsg.triggerId, projects)
        mmClient.openDialog(dialog)
        return ActionResponse("Continue setting in dialog window")
    }

    suspend fun configureScheduler(incoming: Response<SchedulerSubmission>) {
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
}