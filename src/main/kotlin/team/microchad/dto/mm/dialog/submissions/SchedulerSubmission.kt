package team.microchad.dto.mm.dialog.submissions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SchedulerSubmission(
    val selectStatus: String,
    val dayOfWeek: String,
    val hour: String,
    @SerialName("minutes")
    private val _minutes: String?
) : Submission() {
    val cronMinutes: String = _minutes?:"0"
}