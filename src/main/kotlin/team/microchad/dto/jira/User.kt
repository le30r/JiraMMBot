package team.microchad.dto.jira

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var self: String? = null,
    var key: String,
    var name: String,
    var emailAddress: String? = null,
    var displayName: String? = null,
    var active: Boolean? = null,
    var deleted: Boolean? = null,
    var timeZone: String? = null,
    var locale:String? = null
)