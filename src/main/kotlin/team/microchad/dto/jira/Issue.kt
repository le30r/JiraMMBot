package team.microchad.dto.jira

import kotlinx.serialization.*
import kotlinx.serialization.json.JsonNames

@Serializable
data class Issue(
    var expand: String,
    var id: String,
    var self: String,
    var key: String,
    @JsonNames("fields")
    var fields: Field
)