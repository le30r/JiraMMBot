package team.microchad.dto.mm.dialog.elements
import kotlinx.serialization.*

@Serializable
data class TextElement(
    val display_name: String,//	Display name of the field shown to the user in the dialog. Maximum 24 characters.
    val name: String,//	Name of the field element used by the integration. Maximum 300 characters. You should use unique name fields in the same dialog.
    val type: String,//	Set this value to text for a text element.
    val subtype: String?,//	(Optional) One of text, email, number, password (as of v5.14), tel, or url. Default is text. Use this to set which keypad is presented to users on mobile when entering the field.
    val min_length: Int?,//	(Optional) Minimum input length allowed for an element. Default is 0.
    val max_length: Int?,    //(Optional) Maximum input length allowed for an element. Default is 150. If you expect the input to be greater 150 characters, consider using a textarea type element instead.
    val optional: Boolean?,    //(Optional) Set to true if this form element is not required. Default is false.
    val help_text: String?,    //(Optional) Set help text for this form element. Maximum 150 characters.
    val default: String?,    //(Optional) Set a default value for this form element. Maximum 150 characters.
    val placeholder: String?,    //(Optional) A string displayed to help guide users in completing the element. Maximum 150 characters.
)