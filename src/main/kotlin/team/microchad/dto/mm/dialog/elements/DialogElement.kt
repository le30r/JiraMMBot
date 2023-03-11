package team.microchad.dto.mm.dialog.elements

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Interface basically
 */


//TODO: Bring the fields of all child classes in accordance with the conventions for the code.
//TODO: Pass all child classes fields to constructors according to Kotlin features (Default Constructor Options)
@Serializable
sealed class DialogElement {
    @SerialName("display_name")
    abstract val displayName: String
    abstract val name: String
}
