package team.microchad.dto.mm

import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//TODO Rename this class
@Serializable
data class IncomingMsg(
    @SerialName("channel_id")
    val channelId: String,
    @SerialName("channel_name")
    val channelName: String,
    val command: String? = null,
    @SerialName("response_url")
    val responseUrl: String? = null,
    @SerialName("team_domain")
    val teamDomain: String,
    @SerialName("team_id")
    val teamId: String,
    val text: String? = null,
    val token: String? = null,
    @SerialName("trigger_id")
    val triggerId: String,
    @SerialName("user_id")
    val userId: String,
    @SerialName("user_name")
    val userName: String
)

fun createMessageFromParam(params: Parameters): IncomingMsg {
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
