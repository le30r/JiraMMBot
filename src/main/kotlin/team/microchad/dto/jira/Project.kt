package team.microchad.dto.jira

import kotlinx.serialization.Serializable

//TODO: add =null to all nullable fields in DTOs
@Serializable
data class Project(
    var expand: String? = null,
    var self: String?,
    var id: String,
    var key: String,
    var name: String,
    var projectTypeKey: String? = null,
    var archived: Boolean? = null
)