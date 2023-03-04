package team.microchad.dto.mm.dialog.elements

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
@SerialName("radio")
class RadioElement(override val display_name: String, override val name: String) : DialogElement() {
    val options: List<Option>? = null//	(Optional) An array of options for the radio element.
    val help_text: String? = null//	(Optional) Set help text for this form element. Maximum 150 characters.
    val default: String? = null//(Optional) Set a default value for this form element.
}
