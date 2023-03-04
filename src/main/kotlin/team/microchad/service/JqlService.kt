package team.microchad.service

import team.microchad.utils.toUrl


fun getOutstandingIssuesForUser(username: String, project: String): String {
    return "assignee=$username and status!=\"Done\" and project=\"$project\"".toUrl()
}

fun getUserIssuesSortedByStatus(username: String, project: String): String {
    return "assignee=$username ORDER BY status and project=\"$project\"".toUrl()
}

fun getUserIssuesWithStatus(username: String, status: String, project: String): String {
    return "assignee=$username and status=\"$status\" and project=\"$project\"".toUrl()
}

fun getIssuesWithStatus(status: String): String {
    return "status=\"$status\"".toUrl()
}

fun getIssuesWithStatusByProject(status: String, project: String): String {
    return "status=\"$status\"".toUrl()
}

fun getIssuesWithChangedStatus(status: String): String {
    return "status changed to \"$status\"".toUrl()
}

fun getIssuesWithChangedStatusByProject(status: String, project: String): String {
    return "status changed to \"$status\" and project=\"$project\"".toUrl()
}

fun getIssuesWithChangedStatusForDays(status: String, days: Int): String {
    return "status changed to \"$status\" AFTER -${days}d".toUrl()
}

fun getIssuesWithChangedStatusForDaysByProject(status: String, days: Int, project: String): String {
    return "project=\"$project\" and status changed to \"$status\" AFTER -${days}d".toUrl()
}

fun getMyIssuesWithChangedStatusForDays(username: String, status: String, days: Int): String {
    return "assignee=$username and status changed to \"$status\" AFTER -${days}d".toUrl()
}

fun getMyIssuesWithChangedStatusForDaysByProject(username: String, status: String, days: Int, project: String): String {
    return "assignee=$username and project=\"$project\" and status changed to \"$status\" AFTER -${days}d".toUrl()
}

