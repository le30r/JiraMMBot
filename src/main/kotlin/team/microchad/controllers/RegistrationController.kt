package team.microchad.controllers

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

import team.microchad.client.JiraClient
import team.microchad.client.MmClient
import team.microchad.dto.mm.IncomingMsg
import team.microchad.dto.mm.dialog.Response
import team.microchad.dto.mm.dialog.submissions.ProjectRegistrationSubmission
import team.microchad.dto.mm.dialog.submissions.SelectionSubmission
import team.microchad.dto.mm.slash.ActionResponse
import team.microchad.model.entities.ProjectMap
import team.microchad.model.repositories.ProjectMapRepository
import team.microchad.service.UserService
import team.microchad.service.createRegisterJiraDialog
import team.microchad.service.createRegisterProjectDialog

private const val CONTINUE_IN_DIALOG_MESSAGE = "Continue registration in dialog window"

private const val REGISTRATION_SUCCESSFULLY = "Registration successfully"

private const val REGISTRATION_CANCELED = "Registration canceled"

class RegistrationController : KoinComponent {

    private val mmClient: MmClient by inject()
    private val jiraClient: JiraClient by inject()
    private val userService: UserService by inject()
    private val projectMapRepository: ProjectMapRepository by inject()

    suspend fun openUserDialog(incomingMessage: IncomingMsg): ActionResponse {
        val users = jiraClient.getUsers()
        val dialog = createRegisterJiraDialog(incomingMessage.triggerId, users)
        mmClient.openDialog(dialog)
        return ActionResponse(CONTINUE_IN_DIALOG_MESSAGE)
    }

    suspend fun openProjectDialog(incomingMessage: IncomingMsg): ActionResponse {
        val projects = jiraClient.getProjects()
        val dialog = createRegisterProjectDialog(incomingMessage.triggerId, projects)
        mmClient.openDialog(dialog)
        return ActionResponse(CONTINUE_IN_DIALOG_MESSAGE)
    }

    suspend fun registerUser(result: Response<SelectionSubmission>): ActionResponse {
        val returnVal = if (!result.cancelled) {
            with(result) {
                userService.registerUser(userId, submission!!.jiraUser)
            }
            ActionResponse(REGISTRATION_SUCCESSFULLY)
        } else {
            ActionResponse(REGISTRATION_CANCELED)
        }
        return returnVal
    }

    suspend fun registerProject(result: Response<ProjectRegistrationSubmission>) {
        val project = result.submission?.selectProject
        projectMapRepository.create(ProjectMap(project ?: "", result.channelId))
        println(project.toString())
    }
}