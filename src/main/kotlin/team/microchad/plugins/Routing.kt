package team.microchad.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.koin.core.context.GlobalContext
import org.koin.ktor.ext.inject
import team.microchad.client.JiraClient
import team.microchad.client.MikeBot
import team.microchad.dto.mm.IncomingMsg
import team.microchad.dto.mm.OutgoingMsg
import team.microchad.service.MsgService
import team.microchad.service.OutgoingMsgConstructor


fun Application.configureRouting() {
    val mikeBot: MikeBot by inject<MikeBot>()
    routing {

        get("/") {
            call.respondText("Hello, world!")
        }
        post("/") {
            var incomingMsg = call.receive<IncomingMsg>()
            val username = incomingMsg.user_name
            val status = MsgService(incomingMsg).getStatus()
            var jiraJqlResponse = JiraClient().getIssues(username, status)
            var outgoingMsg1 = OutgoingMsgConstructor(jiraJqlResponse, incomingMsg).getFormattedMessage()
            var outgoingMsg = OutgoingMsg(text = outgoingMsg1, channel = incomingMsg.channel_name, username = incomingMsg.user_name.plus("-").plus(status))
            MikeBot().send(outgoingMsg)
        }
    }
}
