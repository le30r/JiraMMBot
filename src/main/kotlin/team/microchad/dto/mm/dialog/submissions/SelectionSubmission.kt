package team.microchad.dto.mm.dialog.submissions

import kotlinx.serialization.Serializable

@Serializable
class SelectionSubmission(
    val jiraUser: String
) : Submission()