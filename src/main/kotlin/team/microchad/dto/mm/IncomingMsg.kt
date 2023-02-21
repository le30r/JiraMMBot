package team.microchad.dto.mm

import kotlinx.serialization.Serializable


@Serializable
data class IncomingMsg(
    val channel_id: String,
    val channel_name: String,
    val team_domain: String,
    val team_id: String,
    val post_id: String,
    val text: String,
    val timestamp: String,
    val token: String,
    val trigger_word: String,
    val user_id: String,
    val user_name: String
)
