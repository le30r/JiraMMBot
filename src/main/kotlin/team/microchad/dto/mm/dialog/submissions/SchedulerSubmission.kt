package team.microchad.dto.mm.dialog.submissions

import kotlinx.serialization.Serializable

@Serializable
class SchedulerSubmission(
    val selectProject: String,
    val radioScheduler: String
) : Submission() {
}