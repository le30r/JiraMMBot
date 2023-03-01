package team.microchad.dto.jira

import kotlinx.serialization.*

@Serializable
data class StatusCategory(
    var self: String,
    var id: Int,
    var key: String,
    var colorName: String,
    var name: String
)