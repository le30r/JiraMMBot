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
    Action(
        "register", "Bind user to Jira", Integration(
            "${Secrets.botHost}/register_dialog"
        )
    ), Action(
        "stats", "Get statistics", Integration(
            "${Secrets.botHost}/statistics_dialog"
        )
    ), Action(
        "scheduler", "Scheduler settings", Integration(
            "${Secrets.botHost}/scheduler_dialog"
        )
    ), Action(
        "commentIssue", "Comment issue", Integration(
            "${Secrets.botHost}/commentIssue_dialog"
        )
    ), Action(
        "comment", "Comment issue", Integration(
            "${Secrets.botHost}/comment_issue_dialog"
        )
    )
)