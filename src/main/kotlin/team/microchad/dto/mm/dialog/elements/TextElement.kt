package team.microchad.dto.mm.dialog.elements


class TextElement(display_name: String, name: String) : DialogElement(display_name, name, "text") {
    val subtype: String? =
        null//	(Optional) One of text, email, number, password (as of v5.14), tel, or url. Default is text. Use this to set which keypad is presented to users on mobile when entering the field.
    val min_length: Int? = null//	(Optional) Minimum input length allowed for an element. Default is 0.
    val max_length: Int? =
        null    //(Optional) Maximum input length allowed for an element. Default is 150. If you expect the input to be greater 150 characters, consider using a textarea type element instead.
    val optional: Boolean? = null    //(Optional) Set to true if this form element is not required. Default is false.
    val help_text: String? = null    //(Optional) Set help text for this form element. Maximum 150 characters.
    val default: String? = null    //(Optional) Set a default value for this form element. Maximum 150 characters.
    val placeholder: String? =
        null    //(Optional) A string displayed to help guide users in completing the element. Maximum 150 characters.
}
