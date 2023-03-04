package team.microchad.dto.jira

import kotlinx.serialization.Serializable

@Serializable
data class Field(
    var summary: String,
    var updated: String?, //TODO( исправить представление даты)
    var description: String?,
    var project: Project,
    var issueType: IssueType
)

@Serializable
class IssueType(var id: String)