package team.microchad.service

import team.microchad.dto.jira.Project
import team.microchad.dto.jira.Status
import team.microchad.dto.jira.User
import team.microchad.dto.mm.dialog.Dialog
import team.microchad.dto.mm.dialog.DialogMessage
import team.microchad.dto.mm.dialog.elements.CheckboxElement
import team.microchad.dto.mm.dialog.elements.Option
import team.microchad.dto.mm.dialog.elements.SelectElement
import team.microchad.dto.mm.dialog.elements.TextElement
import team.microchad.plugins.Secrets

import java.util.UUID


fun createRegisterJiraDialog(triggerId: String, users: Array<User>) = DialogMessage(
    triggerId = triggerId,
    url =  "${Secrets.botHost}/register_user",
    dialog =  getRegisterDialog(users)
)

fun createStatisticsDialog(triggerId: String, statuses: Array<Status>, projects: Array<Project>) = DialogMessage(
    triggerId =  triggerId,
    url = "${Secrets.botHost}/statistics",
    dialog =  getStatsDialog(statuses, projects)
)

fun createSchedulerDialog(triggerId: String,statuses: Array<Status>) = DialogMessage(
    triggerId = triggerId,
    url = "${Secrets.botHost}/scheduler",
    dialog = getSchedulerDialog(statuses)
)


private fun getSchedulerDialog(statuses: Array<Status>) = Dialog(
    callbackId = UUID.randomUUID().toString(),
    title = "Scheduler settings",
    elements = listOf(statusSelect(statuses), dayOfTheWeekSelect(), hourText(), minutesText())
)

private fun getRegisterDialog(users: Array<User>) = Dialog(
    callbackId =UUID.randomUUID().toString(),
    title ="Jira user registration",
    elements = listOf(setSelectJiraUser(users))
)

private fun getStatsDialog(statuses: Array<Status>, projects: Array<Project>) = Dialog(
    callbackId = UUID.randomUUID().toString(),
    title = "Statistics dialog",
    elements = listOf(statusSelect(statuses), projectSelect(projects), checkboxUser(), userSelect())
)

private fun setSelectJiraUser(users: Array<User>) = SelectElement(
    displayName = "Jira user",
    name = "jiraUser",
    options = users.map { Option(it.name, it.key) },
    placeholder = "Choose Jira user",
    helpText = "Choose your nickname in Jira"
)

private fun statusSelect(statuses: Array<Status>) = SelectElement(
    displayName = "Status",
    name = "selectStatus",
    options = statuses.map { Option(it.name, it.name) },
    placeholder = "Jira statuses",
    helpText = "Choose status"
)

private fun projectSelect(projects: Array<Project>) = SelectElement(
    displayName = "Project",
    name = "selectProject",
    options = projects.map { Option(it.name, it.key) },
    placeholder = "Jira projects",
    helpText = "Choose project"
)

private fun checkboxUser() = CheckboxElement(
    displayName = "Find for user",
    name = "userCheckbox",
    optional = true,
    helpText = "Check it, if you want to see statistics for the User",
    default = "false",
    placeholder = "Use user to find stat"
)

private fun userSelect() = SelectElement(
    displayName = "User",
    name="userSelect",
    dataSource = "users",
    optional = true,
    helpText = "Select a user here to search for them"
)

private fun dayOfTheWeekSelect() = SelectElement(
    displayName = "Day of week",
    name = "dayOfWeek",
    options = listOf(
        Option("Monday", "2"),
        Option("Tuesday", "3"),
        Option("Wednesday", "4"),
        Option("Thursday", "5"),
        Option("Friday", "6"),
        Option("Saturday", "7"),
        Option("Sunday", "1")),
    helpText = "Select day of week",
    placeholder = "Day of week"
)

private fun hourText() = TextElement(
    displayName = "Hour",
    name = "hour",
    helpText = "Enter hour",
    subtype = "number",
    placeholder = "Hour",
    maxLength = 2,
    minLength = 2
)

private fun minutesText() = TextElement(
    displayName = "Minutes",
    name = "minutes",
    helpText = "Enter minutes. Empty if 00",
    subtype = "number",
    placeholder = "Minutes",
    maxLength = 2,
    minLength = 2,
    optional = true
)
