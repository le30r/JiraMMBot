package team.microchad.dto.mm.dialog.elements

import kotlinx.serialization.Serializable

@Serializable
data class Option(
    val text: String,
    val value: String
)