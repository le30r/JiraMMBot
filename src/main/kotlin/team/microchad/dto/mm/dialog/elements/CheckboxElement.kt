package team.microchad.dto.mm.dialog.elements

/**
 * optional  (Optional) Set to true if this form element is not required. Default is false.
 * help_text (Optional) Set help text for this form element. Maximum 150 characters.
 * default   (Optional) Set a default value for this form element. true or false.
 * placeholder (Optional) A string displayed to include a label besides the checkbox. Maximum 150 characters.
 */

class CheckboxElement(display_name: String, name: String) : DialogElement(display_name, name, "checkbox") {
    var optional: Boolean? = null
    var help_text: String? = null
    var default: String? = null
    var placeholder: String? = null
}
