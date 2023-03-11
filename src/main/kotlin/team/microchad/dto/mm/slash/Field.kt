package team.microchad.dto.mm.slash

@kotlinx.serialization.Serializable
data class Field(
    val title: String,
    val value: String,
    val short: String
)
