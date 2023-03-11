package team.microchad.service

import team.microchad.utils.toUrlForm


fun getOutstandingIssuesForUser(username: String, project: String): String {
    return "assignee=$username and status!=\"Done\" and project=\"$project\"&fields=id,key,summary,updated, description, project, issue_type".toUrlForm()
}

fun getUserIssuesSortedByStatus(username: String, project: String): String {
    return "assignee=$username ORDER BY status and project=\"$project\"&fields=id,key,summary,updated, description, project, issue_type".toUrlForm()
}

fun getUserIssuesWithStatus(username: String, status: String, project: String): String {
    return "assignee=$username and status=\"$status\" and project=\"$project\"&fields=id,key,summary,updated, description, project, issue_type".toUrlForm()
}

fun getIssuesWithStatus(status: String): String {
    return "status=\"$status\"&fields=id,key,summary,updated, description, project, issue_type".toUrlForm()
}

fun getIssuesWithStatusByProject(status: String, project: String): String {
    return "status=\"$status\"&fields=id,key,summary,updated, description, project, issue_type".toUrlForm()
}
fun getIssuesByStatusExprAndProject(statusExpr: String, project: String): String {
    return "status$statusExpr and project=\"$project\"&fields=id,key,summary,updated, description, project, issue_type".toUrlForm()
}

fun getIssuesWithChangedStatus(status: String): String {
    return "status changed to \"$status\"&fields=id,key,summary,updated, description, project, issue_type".toUrlForm()
}

fun getIssuesWithChangedStatusByProject(status: String, project: String): String {
    return ("status changed to \"$status\" and project=\"$project\"" +
            "&fields=id,key,summary,updated, description, project, issue_type").toUrlForm()
}

fun getIssuesWithChangedStatusForDays(status: String, days: Int): String {
    return "status changed to \"$status\" AFTER -${days}d&fields=id,key,summary,updated, description, project, issue_type".toUrlForm()
}

fun getIssuesWithChangedStatusForDaysByProject(status: String, days: Int, project: String): String {
    return "project=\"$project\" and status changed to \"$status\" AFTER -${days}d&fields=id,key,summary,updated, description, project, issue_type".toUrlForm()
}

fun getIssuesByProject(project: String): String {
    return "project=\"$project\"&fields=id,key,summary,updated, description, project, issue_type".toUrlForm()
}

fun getIssuesByProjectForDays(project: String, days: Int): String {
    return "project=\"$project\" and createdDate >= \"-${days}d\"&fields=id,key,summary,updated, description, project, issue_type"
}

fun getMyIssuesWithChangedStatusForDays(username: String, status: String, days: Int): String {
    return "assignee=$username and status changed to \"$status\" AFTER -${days}d&fields=id,key,summary,updated, description, project, issue_type".toUrlForm()
}

fun getMyIssuesWithChangedStatusForDaysByProject(username: String, status: String, days: Int, project: String): String {
    return "assignee=$username and project=\"$project\" and status changed to \"$status\" AFTER -${days}d&fields=id,key,summary,updated, description, project, issue_type".toUrlForm()
}

