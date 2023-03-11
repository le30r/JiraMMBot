package team.microchad.dto.mm.slash

@kotlinx.serialization.Serializable
data class Integration(
    val url: String,
    val context: Context? = null
)

@kotlinx.serialization.Serializable
data class Context(
    val action: String
)