package team.microchad.plugins


import io.ktor.http.content.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject
import team.microchad.client.MikeBot
import team.microchad.dto.mm.IncomingMsg
import team.microchad.dto.mm.OutgoingMsg


fun Application.configureRouting() {
    val mikeBot by inject<MikeBot>()

    routing {
        get("/"){
            call.respondText("Hello, world!")
        }
        post("/") {
            val incomingMsg = call.receive<IncomingMsg>()
            val outgoingMsg = OutgoingMsg(text = incomingMsg.text, channel = incomingMsg.channel_name)
            mikeBot.parrot(outgoingMsg)
        }
    }
}
