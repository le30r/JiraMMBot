package team.microchad.plugins


import io.ktor.http.content.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
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
            var outgoingMsg = OutgoingMsg(text = incomingMsg.text, channel = incomingMsg.channel_name)
            MikeBot().parrot(outgoingMsg)
        }
    }
}
