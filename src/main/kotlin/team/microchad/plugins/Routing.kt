package team.microchad.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import team.microchad.client.JiraClient
import team.microchad.client.MikeBot
import team.microchad.dto.mm.IncomingMsg
import team.microchad.dto.mm.OutgoingMsg


fun Application.configureRouting() {
    routing {
        get("/"){
            call.respondText("Hello, world!")
        }
        post("/") {
            var incomingMsg = call.receive<IncomingMsg>()
            val username = incomingMsg.user_name
            val status = "done"
            var jiraJqlResponse = JiraClient("mrsaloed", "root").getIssues(username, status)
            var outgoingMsg = OutgoingMsg(text = jiraJqlResponse.toString(), channel = incomingMsg.channel_name, username = incomingMsg.user_name.plus("-").plus(status))
            MikeBot().send(outgoingMsg)
        }
    }
}
