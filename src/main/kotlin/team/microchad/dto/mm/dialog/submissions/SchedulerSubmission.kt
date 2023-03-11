package team.microchad.dto.mm.dialog.submissions

import kotlinx.serialization.Serializable

@Serializable
class SchedulerSubmission(
    val mondayRadio: String,
    val fridayRadio: String,
    val dailyRadio: String
) : Submission() {
}