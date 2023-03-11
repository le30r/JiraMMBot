package team.microchad.dto.jira

import kotlinx.serialization.Serializable
import team.microchad.dto.mm.dialog.submissions.Submission

@Serializable
data class Comment(val body: String, val visibility: Visibility?)

