package team.microchad.controllers

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import team.microchad.client.JiraClient
import team.microchad.client.MmClient
import team.microchad.dto.mm.IncomingMsg
import team.microchad.dto.mm.OutgoingMsg
import team.microchad.dto.mm.dialog.Response
import team.microchad.dto.mm.dialog.submissions.StatisticsSubmission
import team.microchad.dto.mm.slash.ActionResponse
import team.microchad.model.repositories.ProjectMapRepository
import team.microchad.service.UserService
import team.microchad.service.createChooseProjectDialog
import team.microchad.service.createCommentIssueDialog
import team.microchad.service.getIssuesByProject

class IssueController: KoinComponent {
    private val mmClient: MmClient by inject()
    private val jiraClient: JiraClient by inject()
    val userService: UserService by inject()
    val projectMapRepository: ProjectMapRepository by inject()

    suspend fun openProjectDialog(incoming: IncomingMsg): ActionResponse {
        val projects = jiraClient.getProjects()
        val dialog = createChooseProjectDialog(incoming.triggerId, projects)
        mmClient.openDialog(dialog)
        return ActionResponse("Comment in dialog window")
    }

    suspend fun commentIssueDialog(incoming: IncomingMsg): ActionResponse {
        val project = projectMapRepository.findById(incoming.channelId)
        if (project == null) {
            val chatId = mmClient.createDirectChannel(incoming.userId)
            mmClient.sendToDirectChannel(OutgoingMsg(chatId, "Error! You need bind chat to project first!"))
        }
        else {
            val issues = jiraClient.getByJql(getIssuesByProject(project.project))
            val dialog = createCommentIssueDialog(incoming.triggerId, issues.issues?: emptyArray())
            mmClient.openDialog(dialog)
        }
        return ActionResponse("Comment in dialog window")
    }

}