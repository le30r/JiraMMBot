package team.microchad.dto.mm

@kotlinx.serialization.Serializable
data class OutgoingMsg(
    val channel: String,
    val text: String,
    val username: String
) {
}