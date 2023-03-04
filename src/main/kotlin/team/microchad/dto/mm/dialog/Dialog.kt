package team.microchad.dto.mm.dialog

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import team.microchad.dto.mm.dialog.elements.DialogElement

@Serializable
data class Dialog(
    val callback_id: String,
    val title: String,
    val icon_url: String?,
    @Contextual
    val elements: List<DialogElement>,
    @SerialName("notify_on_cancel")
    val notifyOnCancel: Boolean = false
)