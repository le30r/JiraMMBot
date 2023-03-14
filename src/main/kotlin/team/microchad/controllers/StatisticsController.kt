package team.microchad.controllers

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

import team.microchad.client.JiraClient
import team.microchad.client.MmClient
import team.microchad.dto.mm.IncomingMsg
import team.microchad.dto.mm.dialog.Response
import team.microchad.dto.mm.dialog.submissions.StatisticsSubmission
import team.microchad.dto.mm.slash.ActionResponse
import team.microchad.service.*

private const val CONTINUE_IN_DIALOG_WINDOW = "Continue in dialog window"

class StatisticsController : KoinComponent {

    private val mmClient: MmClient by inject()
    private val jiraClient: JiraClient by inject()
    private val userService: UserService by inject()

    suspend fun openDialog(incomingMessage: IncomingMsg): ActionResponse {
        val statuses = jiraClient.getStatuses()
        val projects = jiraClient.getProjects()
        val dialog = createStatisticsDialog(incomingMessage.triggerId, statuses, projects)
        mmClient.openDialog(dialog)
        return ActionResponse(CONTINUE_IN_DIALOG_WINDOW)
    }

    suspend fun sendStatistics(incoming: Response<StatisticsSubmission>) {
        val status = incoming.submission?.selectStatus
        val project = incoming.submission?.selectProject
        val userCheckbox = incoming.submission?.userCheckbox
        val userSelect = incoming.submission?.userSelect
        val jqlRequest = if (userCheckbox == "true") {
            val jiraUser = userService.getJiraUsername(userSelect!!)
            getUserIssuesWithStatus(jiraUser, status ?: "", project ?: "")
        } else {
            getIssuesWithStatusByProject(status ?: "", project ?: "")
        }
        val channelId = mmClient.createDirectChannel(incoming.userId)
        val responseJira = jiraClient.getByJql(jqlRequest)
        val outgoingMessage = getOutgoingMessageForIssues(channelId, responseJira.issues, status.orEmpty())
        mmClient.sendToDirectChannel(outgoingMessage)
    }
}