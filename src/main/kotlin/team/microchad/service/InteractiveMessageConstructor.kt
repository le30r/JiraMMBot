package team.microchad.service

import team.microchad.dto.mm.slash.Action
import team.microchad.dto.mm.slash.Attachment
import team.microchad.dto.mm.slash.Integration
import team.microchad.dto.mm.slash.SlashResponse
import team.microchad.plugins.Secrets

fun createJiraBotMessage(): SlashResponse = SlashResponse(
    attachments = createAttachmentForJiraBot()
)

private fun createAttachmentForJiraBot(): Array<Attachment> = arrayOf(
    Attachment(
        fallback = "fallback",
        color = "00ff00",
        actions = createActionsForJiraBot()
    )
)

private fun createActionsForJiraBot(): Array<Action> = arrayOf(
    registerAction(),
    getStatisticsAction(),
    schedulerSettingsAction(),
    registerProjectAction(),
    commentIssueAction()
)

private fun registerAction() = Action(
    "register", "Bind user to Jira", Integration(
        "${Secrets.botHost}/register_dialog"
    )
)

private fun getStatisticsAction() = Action(
    "stats", "Get statistics", Integration(
        "${Secrets.botHost}/statistics_dialog"
    )
)

private fun schedulerSettingsAction() = Action(
    "scheduler", "Scheduler settings", Integration(
        "${Secrets.botHost}/scheduler_dialog"
    )
)

private fun registerProjectAction() = Action(
    "registerProject", "Bind project to channel", Integration(
        "${Secrets.botHost}/register-project_dialog"
    )
)

private fun commentIssueAction() = Action(
    "commentIssue", "Comment issue", Integration(
        "${Secrets.botHost}/comment_dialog"
    )
)