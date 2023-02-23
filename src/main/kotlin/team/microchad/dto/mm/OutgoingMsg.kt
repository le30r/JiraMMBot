package team.microchad.dto.mm

//TODO Rename this class
@kotlinx.serialization.Serializable
data class OutgoingMsg(
    val channel: String,
    val text: String,
    val username: String
)