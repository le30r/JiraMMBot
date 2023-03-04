package team.microchad.dto.jira

import kotlinx.serialization.Serializable

@Serializable
data class Field(
    var summary: String,
    var updated: String? = null,  //TODO( исправить представление даты)
    var description: String? = null,
    var project: Project,
    var issueType: IssueType? = null
)

@Serializable
class IssueType(var id: String)