package team.microchad.dto.jira

import kotlinx.serialization.Serializable

@Serializable
data class Project(
    var expand: String? = null,
    var self: String? = null,
    var id: String,
    var key: String,
    var name: String,
    var projectTypeKey: String? = null,
    var archived: Boolean? = null
)
