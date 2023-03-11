package team.microchad.service

import team.microchad.dto.jira.Issue
import team.microchad.dto.jira.Project
import team.microchad.dto.jira.Status
import team.microchad.dto.jira.User
import team.microchad.dto.mm.dialog.Dialog
import team.microchad.dto.mm.dialog.DialogMessage
import team.microchad.dto.mm.dialog.elements.*
import team.microchad.model.entities.ProjectMap
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

fun createSchedulerDialog(triggerId: String, projects: Array<Project>, entity: ProjectMap) = DialogMessage(
    triggerId = triggerId,
    url = "${Secrets.botHost}/scheduler",
    dialog = getSchedulerDialog(entity)
)


fun createChooseProjectDialog(triggerId: String, projects: Array<Project>) = DialogMessage(
    triggerId =  triggerId,
    url = "${Secrets.botHost}/chooseIssue_dialog",
    dialog = getChooseProjectDialog(projects)
)

fun createRegisterProjectDialog(triggerId: String, projects: Array<Project>) = DialogMessage (
    triggerId = triggerId,
    url = "${Secrets.botHost}/register-project",
    dialog = getRegisterProjectDialog(projects)
)
fun createCommentIssueDialog(triggerId: String, issues: Array<Issue>) = DialogMessage (
    triggerId = triggerId,
    url = "${Secrets.botHost}/comment",
    dialog = getCommentIssueDialog(issues)
)

private fun getCommentIssueDialog(issues: Array<Issue>) = Dialog(
    callbackId = UUID.randomUUID().toString(),
    title = "Register your channel with project",
    elements = listOf(issuesSelect(issues), TextareaElement("Comment", "comment",
        placeholder = "Type your comment"))
)
private fun getRegisterProjectDialog(projects: Array<Project>) = Dialog(
    callbackId = UUID.randomUUID().toString(),
    title = "Register your channel with project",
    elements = listOf(projectSelect(projects))
)

private fun getChooseProjectDialog(projects: Array<Project>) = Dialog(
    callbackId = UUID.randomUUID().toString(),
    title = "Comment your issue!",
    elements = listOf(projectSelect(projects))
)


private fun getSchedulerDialog(entity: ProjectMap) = Dialog(
    callbackId = UUID.randomUUID().toString(),
    title = "Scheduler settings",
    elements = listOf(mondayRadiobutton(entity), fridayRadiobutton(entity), dailyRadiobutton(entity))
)

private fun mondayRadiobutton(entity: ProjectMap) =  RadioElement (
    displayName = "Monday",
    name = "mondayRadio",
    options = listOf(Option("on", "on"), Option("off", "off")),
    default =  if (entity.monday) "on" else "off"
)

private fun fridayRadiobutton(entity: ProjectMap)=  RadioElement (
    displayName = "Friday",
    name = "fridayRadio",
    options = listOf(Option("on", "on"), Option("off", "off")),
    default = if (entity.friday) "on" else "off"
)

private fun dailyRadiobutton(entity: ProjectMap)=  RadioElement (
    displayName = "Every day",
    name = "dailyRadio",
    options = listOf(Option("on", "on"), Option("off", "off")),
    default = if (entity.everyday) "on" else "off"
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
    options = users.map { Option(it.name, it.name) },
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

private fun issuesSelect(issues: Array<Issue>?) = SelectElement(
    displayName = "Choose issue",
    name = "issue",
    helpText = "Choose issues to comment",
    options = issues?.map { Option("${it.key} - ${it.fields.summary}", it.key) }
)

private fun commentArea() = TextareaElement(
    displayName = "Comment here",
    name = "commentArea",
    placeholder = "Comment Here"
)
