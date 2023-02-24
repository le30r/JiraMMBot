package team.microchad.dto.jira

data class Comment(val body: String, val visibility: Visibility?)


data class Visibility(val type: String, val value: String)
