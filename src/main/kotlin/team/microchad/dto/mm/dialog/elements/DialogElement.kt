package team.microchad.dto.mm.dialog.elements

import kotlinx.serialization.Serializable

/**
 * Interface basically
 */

@Serializable
open class DialogElement(
    val display_name: String,
    val name: String,
    val type: String
)
