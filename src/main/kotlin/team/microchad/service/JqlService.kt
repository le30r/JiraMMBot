package team.microchad.service

import team.microchad.utils.toUrl


fun getOutstandingIssuesForUser(username: String): String {
    val jqlQuery: String = "assignee=$username and status!=\"Done\""
    return jqlQuery.toUrl()
}

fun getUserIssuesSortedByStatus(username: String): String {
    val jqlQuery: String = "assignee=$username ORDER BY status"
    return jqlQuery.toUrl()
}

fun getUserIssuesWithStatus(username: String, status: String): String {
    val jqlQuery: String = "assignee=$username and status=\"$status\""
    return jqlQuery.toUrl()
}

fun getIssuesWithStatus(status: String): String {
    val jqlQuery: String = "status=\"$status\""
    return jqlQuery.toUrl()
}

fun getIssuesWithChangedStatus(status: String): String {
    val jqlQuery: String = "status changed to \"$status\""
    return jqlQuery.toUrl()
}

fun getIssuesWithChangedStatusForDays(status: String, days: Int): String {
    val jqlQuery: String = "status changed to \"$status\" AFTER -${days}d"
    return jqlQuery.toUrl()
}

fun getMyIssuesWithChangedStatusForDays(username: String, status: String, days: Int): String {
    val jqlQuery: String = "assignee=$username and status changed to \"$status\" AFTER -${days}d"
    return jqlQuery.toUrl()
}

