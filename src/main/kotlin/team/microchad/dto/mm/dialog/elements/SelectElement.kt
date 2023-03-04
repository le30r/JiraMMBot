package team.microchad.dto.mm.dialog.elements

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("select")
data class SelectElement(
    @SerialName("display_name")
    override val displayName: String,
    override val name: String,
    @SerialName("data_source")
    val dataSource: String? =
        null, //	(Optional) One of users, or channels. If none specified, assumes a manual list of options is provided by the integration.
    val optional: Boolean? = null, //	(Optional) Set to true if this form element is not required. Default is false.
    val options: List<Option>? = null, //	(Optional) Set to true if this form element is not required. Default is false.
    @SerialName("help_text")
    val helpText: String? = null, //	(Optional)
    val default: String? =
        null,//	(Optional) (Optional) Set a default value for this form element. Maximum 3,000 characters.
    var placeholder: String? =
        null //	(Optional) A string displayed to help guide users in completing the element. Maximum 3,000 characters.
) : DialogElement()
