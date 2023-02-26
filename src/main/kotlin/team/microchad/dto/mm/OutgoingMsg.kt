package team.microchad.dto.mm

import kotlinx.serialization.*

//TODO Rename this class
@Serializable
data class OutgoingMsg(
    val channel_id: String,
    val message: String
)