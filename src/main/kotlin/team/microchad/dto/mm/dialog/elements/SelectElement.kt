package team.microchad.dto.mm.dialog.elements

import kotlinx.serialization.*

@Serializable
class SelectElement(override val display_name: String, override val name: String) : DialogElement {
    override val type = "select" //	Set this value to select for a select element.
    val data_source: String? =
        null //	(Optional) One of users, or channels. If none specified, assumes a manual list of options is provided by the integration.
    val optional: Boolean? = null //	(Optional) Set to true if this form element is not required. Default is false.
    val options: List<Option>? = null //	(Optional) Set to true if this form element is not required. Default is false.
    val help_text: String? = null //	(Optional)
    val default: String? =
        null //	(Optional) (Optional) Set a default value for this form element. Maximum 3,000 characters.
    val placeholder: String? =
        null //	(Optional) A string displayed to help guide users in completing the element. Maximum 3,000 characters.
}