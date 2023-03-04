package team.microchad.dto.mm.dialog.elements

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("text")
data class TextareaElement(
    @SerialName("display_name")
    override val displayName: String,
    override val name: String,
    val subtype: String? =
        null,//	(Optional) One of text, email, number, password (as of v5.14), tel, or url. Default is text. Use this to set which keypad is presented to users on mobile when entering the field.
    @SerialName("min_length")
    val minLength: Int? = null,//	(Optional) Minimum input length allowed for an element. Default is 0.
    @SerialName("max_length")
    val maxLength: Int? = null,//	(Optional) Maximum input length allowed for an element. Default is 3000.
    val optional: Boolean? = null,//	(Optional) Set to true if this form element is not required. Default is false.
    @SerialName("help_text")
    val helpText: String? = null,//	(Optional) Set help text for this form element. Maximum 150 characters.
    val default: String? = null,//	(Optional) Set a default value for this form element. Maximum 3000 characters.
    val placeholder: String? =
        null//	(Optional) A string displayed to help guide users in completing the element. Maximum 3000 characters.

) : DialogElement()
