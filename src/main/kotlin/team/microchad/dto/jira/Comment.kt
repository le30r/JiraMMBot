package team.microchad.dto.jira

import kotlinx.serialization.Serializable

@Serializable
data class Comment(val body: String, val visibility: Visibility? = null)

