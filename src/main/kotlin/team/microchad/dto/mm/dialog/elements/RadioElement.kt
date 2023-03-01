package team.microchad.dto.mm.dialog.elements


class RadioElement(display_name: String, name: String) : DialogElement(display_name, name, "radio") {
    val options: List<Option>? = null//	(Optional) An array of options for the radio element.
    val help_text: String? = null//	(Optional) Set help text for this form element. Maximum 150 characters.
    val default: String? = null//(Optional) Set a default value for this form element.
}
