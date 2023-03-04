package team.microchad.dto.mm.dialog

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import team.microchad.dto.mm.dialog.submissions.Submission

@Serializable
data class Response<T: Submission> (
    val type: String,
    @SerialName("callback_id")
    val callbackId: String,
    val state: String,
    @SerialName("user_id")
    val userId: String,
    @SerialName("channel_id")
    val channelId: String,
    @SerialName("team_id")
    val teamId: String,
    val submission: T?,
    val cancelled: Boolean
)