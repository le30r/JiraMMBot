package team.microchad.dto.mm.dialog

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DialogMessage(
    @SerialName("trigger_id")
    val triggerId: String,
    val url: String,
    val dialog: Dialog
)