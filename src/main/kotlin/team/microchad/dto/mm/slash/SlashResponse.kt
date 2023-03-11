package team.microchad.dto.mm.slash

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SlashResponse(
    val text: String? = null,
    val attachments: Array<Attachment>? = null,
    @SerialName("response_type")
    val responseType: String? = null,
    val username: String? = null,
    val channelId: String? = null,
    val iconUrl: String? = null,
    val gotoLocation: String? = null,
)
