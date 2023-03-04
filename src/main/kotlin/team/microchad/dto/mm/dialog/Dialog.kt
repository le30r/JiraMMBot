package team.microchad.dto.mm.dialog

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import team.microchad.dto.mm.dialog.elements.DialogElement

@Serializable
data class Dialog(
    @SerialName("callback_id")
    val callbackId: String,
    val title: String,
    @SerialName("icon_url")
    val iconUrl: String?,
    @Contextual
    val elements: List<DialogElement>,
    @SerialName("notify_on_cancel")
    val notifyOnCancel: Boolean = false
)