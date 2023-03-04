package team.microchad.dto.mm

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//TODO Rename this class
@Serializable
data class OutgoingMsg(
    @SerialName("channel_id")
    val channelId: String,
    val message: String
)