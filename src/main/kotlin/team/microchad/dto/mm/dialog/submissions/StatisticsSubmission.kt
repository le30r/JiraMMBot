package team.microchad.dto.mm.dialog.submissions

import kotlinx.serialization.Serializable

@Serializable
class StatisticsSubmission(
    val selectProject: String,
    val selectStatus: String,
    val userCheckbox: String?,
    val userSelect: String?
) : Submission()