package team.microchad.dto.mm
import kotlinx.serialization.Serializable
@Serializable
data class User (
    val id: String,
    val username: String
)
