package team.microchad.dto.mm.dialog.elements

import kotlinx.serialization.*


class CheckboxElement(display_name: String, name: String) : DialogElement(display_name, name, "checkbox") {
    var optional: Boolean? = null //   (Optional) Set to true if this form element is not required. Default is false.
    var help_text: String? = null //  (Optional) Set help text for this form element. Maximum 150 characters.
    var default: String? = null //  (Optional) Set a default value for this form element. true or false.
    var placeholder: String? =
        null //   (Optional) A string displayed to include a label besides the checkbox. Maximum 150 characters.
}
