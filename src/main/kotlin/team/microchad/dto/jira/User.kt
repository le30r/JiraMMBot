package team.microchad.dto.jira

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var self: String?,
    var key: String,
    var name: String?,
    var emailAddress: String?,
    var displayName: String?,
    var active: Boolean?,
    var deleted: Boolean?,
    var timeZone: String?,
    var locale:String?
)