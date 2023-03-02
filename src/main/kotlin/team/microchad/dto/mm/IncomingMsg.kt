package team.microchad.dto.mm

import io.ktor.http.*
import kotlinx.serialization.Serializable

//TODO Rename this class
@Serializable
data class IncomingMsg(
    val channel_id: String,
    val channel_name: String,
    val command: String,
    val response_url: String,
    val team_domain: String,
    val team_id: String,
    val text: String,
    val token: String,
    val trigger_id: String,
    val user_id: String,
    val user_name: String
)

fun fromParam(params: Parameters): IncomingMsg {
    return IncomingMsg(
        params["channel_id"] ?: "",
        params["channel_name"] ?: "",
        params["command"] ?: "",
        params["response_url"] ?: "",
        params["team_domain"] ?: "",
        params["team_id"] ?: "",
        params["text"] ?: "",
        params["token"] ?: "",
        params["trigger_id"] ?: "",
        params["user_id"] ?: "",
        params["user_name"] ?: ""
    )
}
