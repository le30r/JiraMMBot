package team.microchad.dto.jira

@kotlinx.serialization.Serializable
data class Comment(val body: String, val visibility: Visibility?)

