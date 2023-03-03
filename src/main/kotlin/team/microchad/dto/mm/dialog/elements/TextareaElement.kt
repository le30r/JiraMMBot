package team.microchad.dto.mm.dialog.elements

/**
 * subtype (Optional) One of text, email, number, password (as of v5.14), tel, or url. Default is text. Use this to set which keypad is presented to users on mobile when entering the field.
 * min_length (Optional) Minimum input length allowed for an element. Default is 0.
 * max_length (Optional) Maximum input length allowed for an element. Default is 3000.
 * optional   (Optional) Set to true if this form element is not required. Default is false.
 * help_text  (Optional) Set help text for this form element. Maximum 150 characters.
 * default    (Optional) Set a default value for this form element. Maximum 3000 characters.
 * placeholder (Optional) A string displayed to help guide users in completing the element. Maximum 3000 characters.
 */

class TextareaElement(display_name: String, name: String) : DialogElement(display_name, name, "textarea") {
    val subtype: String? = null
    val min_length: Int? = null
    val max_length: Int? = null
    val optional: Boolean? = null
    val help_text: String? = null
    val default: String? = null
    val placeholder: String? = null
}