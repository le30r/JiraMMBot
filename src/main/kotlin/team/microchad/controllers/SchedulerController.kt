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
import team.microchad.model.repositories.ProjectMapRepository
import team.microchad.service.createSchedulerDialog

private const val CONTINUE_SETTING_IN_DIALOG_WINDOW = "Continue setting in dialog window"

private const val SCHEDULER_SETTINGS_FAILED = "Scheduler setting is failed! You need to bind project first!"

private const val ERROR = "Error"

private const val BIND_PROJECT_ERROR = "Error! You need bind project to chat first!"

private const val SETTINGS_CHANGED = "Settings changed"

class SchedulerController : KoinComponent {

    private val mmClient: MmClient by inject()
    private val jiraClient: JiraClient by inject()
    private val projectMapRepository: ProjectMapRepository by inject()


    suspend fun openDialog(incomingMsg: IncomingMsg): ActionResponse {
        val projects = jiraClient.getProjects()
        val entity = projectMapRepository.findById(incomingMsg.channelId)
        return if (entity != null) {
            val dialog = createSchedulerDialog(incomingMsg.triggerId, projects, entity)
            mmClient.openDialog(dialog)
            ActionResponse(CONTINUE_SETTING_IN_DIALOG_WINDOW)
        } else {
            val chat = mmClient.createDirectChannel(incomingMsg.userId)
            val message = OutgoingMsg(chat, SCHEDULER_SETTINGS_FAILED)
            mmClient.sendToDirectChannel(message)
            ActionResponse(ERROR)
        }

    }

    suspend fun configureScheduler(incoming: Response<SchedulerSubmission>) = with(incoming) {
        val monday = submission?.mondayRadio
        val friday = submission?.fridayRadio
        val daily = submission?.dailyRadio

        val entity = projectMapRepository.findById(channelId)?.let {
            it.friday = friday == "on"
            it.monday = monday == "on"
            it.everyday = daily == "on"
            it
        }
        val message = if (entity == null) {
            BIND_PROJECT_ERROR
        } else {
            projectMapRepository.update(entity.chat, entity)
            SETTINGS_CHANGED
        }

        val chatId = mmClient.createDirectChannel(incoming.userId)
        val outgoingMessage = OutgoingMsg(chatId, message)
        mmClient.sendToDirectChannel(outgoingMessage)
    }

}
