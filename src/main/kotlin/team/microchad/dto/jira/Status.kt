package team.microchad.dto.jira

import kotlinx.serialization.*
import kotlinx.serialization.json.JsonNames

@Serializable
data class Status(
    var self: String,
    var description: String,
    var iconUrl: String,
    var name: String,
    var id: String,
    @JsonNames("statusCategory")
    var statusCategory: StatusCategory
)