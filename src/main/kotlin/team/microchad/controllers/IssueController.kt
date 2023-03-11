package team.microchad.controllers

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import team.microchad.client.JiraClient
import team.microchad.client.MmClient
import team.microchad.dto.mm.IncomingMsg
import team.microchad.dto.mm.dialog.Response
import team.microchad.dto.mm.dialog.submissions.StatisticsSubmission
import team.microchad.dto.mm.slash.ActionResponse
import team.microchad.service.UserService
import team.microchad.service.createChooseProjectDialog
import team.microchad.service.getIssuesByProject

class IssueController: KoinComponent {
    private val mmClient: MmClient by inject()
    private val jiraClient: JiraClient by inject()
    val userService: UserService by inject()

    suspend fun openProjectDialog(incoming: IncomingMsg): ActionResponse {
        val projects = jiraClient.getProjects()
        val dialog = createChooseProjectDialog(incoming.triggerId, projects)
        mmClient.openDialog(dialog)
        return ActionResponse("Comment in dialog window")
    }

    suspend fun createIssue(incoming: Response<StatisticsSubmission>) {

    }

}