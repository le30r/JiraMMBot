package team.microchad.controllers

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

import team.microchad.client.JiraClient
import team.microchad.client.MmClient
import team.microchad.dto.mm.IncomingMsg
import team.microchad.dto.mm.OutgoingMsg
import team.microchad.dto.mm.slash.ActionResponse
import team.microchad.model.repositories.ProjectMapRepository
import team.microchad.service.createChooseProjectDialog
import team.microchad.service.createCommentIssueDialog
import team.microchad.service.getIssuesByProject

private const val OPEN_PROJECT_DIALOG_RESPONSE = "Comment in dialog window"

private const val ERROR_NULL_PROJECT_MESSAGE = "Error! You need bind chat to project first!"

class IssueController : KoinComponent {
    private val mmClient: MmClient by inject()
    private val jiraClient: JiraClient by inject()
    private val projectMapRepository: ProjectMapRepository by inject()

    suspend fun openProjectDialog(incoming: IncomingMsg): ActionResponse {
        val projects = jiraClient.getProjects()
        val dialog = createChooseProjectDialog(incoming.triggerId, projects)
        mmClient.openDialog(dialog)
        return ActionResponse(OPEN_PROJECT_DIALOG_RESPONSE)
    }

    suspend fun commentIssueDialog(incoming: IncomingMsg): ActionResponse {
        val project = projectMapRepository.findById(incoming.channelId)
        if (project == null) {
            val chatId = mmClient.createDirectChannel(incoming.userId)
            mmClient.sendToDirectChannel(OutgoingMsg(chatId, ERROR_NULL_PROJECT_MESSAGE))
        } else {
            val issues = jiraClient.getByJql(getIssuesByProject(project.project))
            val dialog = createCommentIssueDialog(incoming.triggerId, issues.issues ?: emptyArray())
            mmClient.openDialog(dialog)
        }
        return ActionResponse(OPEN_PROJECT_DIALOG_RESPONSE)
    }

}