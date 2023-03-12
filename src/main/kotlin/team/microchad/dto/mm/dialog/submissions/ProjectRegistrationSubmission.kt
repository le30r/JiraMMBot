package team.microchad.dto.mm.dialog.submissions

import kotlinx.serialization.Serializable

@Serializable
class ProjectRegistrationSubmission(
    val selectProject: String
) : Submission()