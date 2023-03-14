package team.microchad.controllers

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

import team.microchad.client.JiraClient
import team.microchad.client.MmClient
import team.microchad.dto.mm.IncomingMsg
import team.microchad.dto.mm.OutgoingMsg
import team.microchad.dto.mm.dialog.Response
import team.microchad.dto.mm.dialog.submissions.ProjectRegistrationSubmission
import team.microchad.dto.mm.dialog.submissions.SelectionSubmission
import team.microchad.dto.mm.slash.ActionResponse
import team.microchad.model.entities.ProjectMap
import team.microchad.model.repositories.ProjectMapRepository
import team.microchad.service.*

private const val CONTINUE_IN_DIALOG_MESSAGE = "Continue registration in dialog window"

private const val CONTINUE_IN_DIRECT_CHANNEL = "Continue in direct channel"


private const val REGISTRATION_SUCCESSFULLY = "Registration successfully"

private const val REGISTRATION_ERROR = "Registration canceled. Please, try again later"

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
        var message: OutgoingMsg
        with(result) {
            val directChannel = mmClient.createDirectChannel(result.userId)
            message = try {
                userService.registerUser(userId, submission!!.jiraUser)
                OutgoingMsg(
                    directChannel,
                    REGISTRATION_SUCCESSFULLY
                )
            } catch (e: Exception) {
                OutgoingMsg(
                    directChannel,
                    REGISTRATION_ERROR
                )
            }
        }
        mmClient.sendToDirectChannel(message)
        return ActionResponse(CONTINUE_IN_DIRECT_CHANNEL)
    }

    suspend fun registerProject(result: Response<ProjectRegistrationSubmission>) {
        val project = result.submission?.selectProject
        val directChannel = mmClient.createDirectChannel(result.userId)
        val message = try {
            projectMapRepository.create(ProjectMap(project ?: "", result.channelId))
            OutgoingMsg(
                directChannel,
                "Your project successfully registered"
            )
        } catch (e: Exception) {
            OutgoingMsg(
                directChannel,
                "Your channel already bound"
            )
        }
        mmClient.sendToDirectChannel(message)
    }
}