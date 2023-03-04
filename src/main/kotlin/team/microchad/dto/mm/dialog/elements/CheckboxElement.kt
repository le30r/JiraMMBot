package team.microchad.dto.mm.dialog.elements

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@SerialName("bool")
data class CheckboxElement(
    @SerialName("display_name")
    override val displayName: String,
    override val name: String,
    var optional: Boolean? = null, //   (Optional) Set to true if this form element is not required. Default is false.
    @SerialName("help_text")
    var helpText: String? = null, //  (Optional) Set help text for this form element. Maximum 150 characters.
    val default: String? = null, //  (Optional) Set a default value for this form element. true or false.
    val placeholder: String? = null //   (Optional) A string displayed to include a label besides the checkbox. Maximum 150 characters.
) : DialogElement()
