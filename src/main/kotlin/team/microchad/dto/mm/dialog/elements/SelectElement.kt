package team.microchad.dto.mm.dialog.elements

import team.microchad.dto.mm.dialog.elements.Option
import kotlinx.serialization.*

@Serializable
data class SelectElement(
    val display_name: String,//	Display name of the field shown to the user in the dialog. Maximum 24 characters.
    val name: String,//	Name of the field element used by the integration. Maximum 300 characters. You should use unique name fields in the same dialog.
    val type: String,//	Set this value to select for a select element.
    val data_source: String?,//	(Optional) One of users, or channels. If none specified, assumes a manual list of options is provided by the integration.
    val optional: Boolean?,//	(Optional) Set to true if this form element is not required. Default is false.
    val options: Array<Option>?,//	(Optional) Set to true if this form element is not required. Default is false.
    val help_text: String?,//	(Optional)
    val default: String?,//	(Optional) (Optional) Set a default value for this form element. Maximum 3,000 characters.
    val placeholder: String?,//	(Optional) A string displayed to help guide users in completing the element. Maximum 3,000 characters.
)