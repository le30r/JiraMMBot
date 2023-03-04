package team.microchad.dto.mm.dialog.elements

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("select")
class SelectElement(override val display_name: String,
                    override val name: String) : DialogElement() {
    var data_source: String? =
        null //	(Optional) One of users, or channels. If none specified, assumes a manual list of options is provided by the integration.
    var optional: Boolean? = null //	(Optional) Set to true if this form element is not required. Default is false.
    var options: List<Option>? = null //	(Optional) Set to true if this form element is not required. Default is false.
    var help_text: String? = null //	(Optional)
    var default: String? =
        null //	(Optional) (Optional) Set a default value for this form element. Maximum 3,000 characters.
    var placeholder: String? =
        null //	(Optional) A string displayed to help guide users in completing the element. Maximum 3,000 characters.
}
