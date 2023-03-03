package team.microchad.dto.mm.dialog.elements

/** options (Optional) An array of options for the radio element.
 * help_text (Optional) Set help text for this form element. Maximum 150 characters.
 * default (Optional) Set a default value for this form element.**/
class RadioElement(display_name: String, name: String) : DialogElement(display_name, name, "radio") {
    val options: List<Option>? = null
    val help_text: String? = null
    val default: String? = null
}
