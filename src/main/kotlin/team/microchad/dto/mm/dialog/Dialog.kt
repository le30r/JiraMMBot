package team.microchad.dto.mm.dialog

import team.microchad.dto.mm.dialog.elements.DialogElement
import team.microchad.dto.mm.dialog.elements.TextElement

import kotlinx.serialization.*

@Serializable
data class Dialog(
    val callback_id: String,
    val title: String,
    val icon_url: String?,
    val elements: List<TextElement>
)