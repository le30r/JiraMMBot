package team.microchad.dto.mm.dialog

import kotlinx.serialization.Serializable

@Serializable
data class DialogMessage(
    val trigger_id: String,
    val url: String,
    val dialog: Dialog
)