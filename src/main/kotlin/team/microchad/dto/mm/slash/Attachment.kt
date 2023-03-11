package team.microchad.dto.mm.slash

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Attachment(
    val fallback: String,
    val color: String,
    val pretext: String? = null,
    val text: String? = null,
    @SerialName("author_name")
    val authorName: String? = null,
    @SerialName("author_link")
    val authorLink: String? = null,
    @SerialName("author_icon")
    val authorIcon: String? = null,
    val title: String? = null,
    val fields: Array<Field>? = null,
    val actions: Array<Action>? = null,
    @SerialName("image_url")
    val imageUrl: String? = null,
    @SerialName("thumb_url")
    val thumbUrl: String? = null,
    val footer: String? = null,
    @SerialName("footer_icon")
    val footerIcon: String? = null
)
