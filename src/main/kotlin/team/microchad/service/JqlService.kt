package team.microchad.service

import team.microchad.utils.toUrl


fun getOutstandingIssuesForUser(username: String): String {
    return "assignee=$username and status!=\"Done\"".toUrl()
}

fun getUserIssuesSortedByStatus(username: String): String {
    return "assignee=$username ORDER BY status".toUrl()
}

fun getUserIssuesWithStatus(username: String, status: String): String {
    return "assignee=$username and status=\"$status\"".toUrl()
}

fun getIssuesWithStatus(status: String): String {
    return "status=\"$status\"".toUrl()
}

fun getIssuesWithChangedStatus(status: String): String {
    return "status changed to \"$status\"".toUrl()
}

fun getIssuesWithChangedStatusForDays(status: String, days: Int): String {
    return "status changed to \"$status\" AFTER -${days}d".toUrl()
}

fun getMyIssuesWithChangedStatusForDays(username: String, status: String, days: Int): String {
    return "assignee=$username and status changed to \"$status\" AFTER -${days}d".toUrl()
}

