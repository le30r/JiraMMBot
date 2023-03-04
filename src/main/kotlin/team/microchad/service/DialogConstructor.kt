package team.microchad.service

import team.microchad.dto.jira.User
import team.microchad.dto.mm.dialog.Dialog
import team.microchad.dto.mm.dialog.DialogMessage
import team.microchad.dto.mm.dialog.elements.Option
import team.microchad.dto.mm.dialog.elements.SelectElement
import team.microchad.plugins.Secrets
import java.util.UUID


fun createRegisterJiraDialog(triggerId: String, users: Array<User>) = DialogMessage(
    triggerId,
    "${Secrets.botHost}/register_user",
    getRegisterDialog(users)
)

private fun getRegisterDialog(users: Array<User>) = Dialog(
    UUID.randomUUID().toString(),
    "Jira user registration",
    elements = listOf(setSelectJiraUser(users))
)

private fun setSelectJiraUser(users: Array<User>) = SelectElement(
    "Jira user",
    "jiraUser",
    options = users.map { Option(it.name, it.key) },
    placeholder = "Choose Jira user",
    helpText = "Choose your nickname in Jira"
)
