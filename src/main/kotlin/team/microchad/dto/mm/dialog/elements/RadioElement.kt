package team.microchad.dto.mm.dialog.elements


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("radio")
data class RadioElement(
    @SerialName("display_name")
    override val displayName: String,
    override val name: String,
    val options: List<Option>? = null,//	(Optional) An array of options for the radio element.
    @SerialName("help_text")
    val helpText: String? = null,//	(Optional) Set help text for this form element. Maximum 150 characters.
    val default: String? = null//(Optional) Set a default value for this form element.
) : DialogElement()
