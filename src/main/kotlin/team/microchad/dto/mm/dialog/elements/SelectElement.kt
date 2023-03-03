package team.microchad.dto.mm.dialog.elements

/**
 * data_source (Optional) One of users, or channels. If none specified, assumes a manual list of options is provided by the integration.
 * optional    (Optional) Set to true if this form element is not required. Default is false.
 * options     (Optional) Set to true if this form element is not required. Default is false.
 * help_text   (Optional)
 * default     (Optional) (Optional) Set a default value for this form element. Maximum 3,000 characters.
 * placeholder (Optional) A string displayed to help guide users in completing the element. Maximum 3,000 characters.
 */

class SelectElement(display_name: String, name: String) : DialogElement(display_name, name, "select") {
    val data_source: String? = null
    val optional: Boolean? = null
    val options: List<Option>? = null
    val help_text: String? = null
    val default: String? = null
    val placeholder: String? = null
}