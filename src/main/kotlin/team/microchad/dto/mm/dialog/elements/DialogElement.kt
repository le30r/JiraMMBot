package team.microchad.dto.mm.dialog.elements

import kotlinx.serialization.*

@Serializable
sealed class DialogElement {
    abstract val display_name: String
    abstract val name: String
}
