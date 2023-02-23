package team.microchad.dto.jira

import kotlinx.serialization.json.JsonNames

@kotlinx.serialization.Serializable
data class Issue(
    var expand: String,
    var id: String,
    var self: String,
    var key: String,
    @JsonNames("fields")
    var fields: Field
)