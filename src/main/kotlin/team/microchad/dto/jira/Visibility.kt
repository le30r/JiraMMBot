package team.microchad.dto.jira

import kotlinx.serialization.*

@Serializable
data class Visibility(val type: String, val value: String)
