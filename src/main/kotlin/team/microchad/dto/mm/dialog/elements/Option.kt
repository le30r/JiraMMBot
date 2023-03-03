package team.microchad.dto.mm.dialog.elements

import kotlinx.serialization.Serializable

/**
 * Can put in select and somewhere more (c) saloed 02.03.2023
 */

@Serializable
data class Option(
    val text: String,
    val value: String
)