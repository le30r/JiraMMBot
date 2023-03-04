package team.microchad.dto.jira

import kotlinx.serialization.Serializable

@Serializable
data class Project(
    var expand: String?,
    var self: String?,
    var id: String,
    var key: String,
    var name: String,
    var projectTypeKey: String?,
    var archived: Boolean?
)