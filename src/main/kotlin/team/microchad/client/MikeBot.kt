package team.microchad.client

import io.ktor.client.*
import io.ktor.client.engine.java.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import team.microchad.dto.mm.OutgoingMsg

class MikeBot {
    companion object {
        private const val MM_WEBHOOK = "http://tin-workshop.ddns.net:8065/hooks/"
    }

    private val client = HttpClient(Java) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

    }

//    private var request: HttpRequest = {
//
//    }

    suspend fun parrot(msg: OutgoingMsg) {
        val response: HttpResponse = client.post(MM_WEBHOOK.plus("5mj4ntowb7n3ddkb8hqbuw8sde")) {


            headers {
                append(HttpHeaders.ContentType, "application/json")
            }
            setBody(msg)
        }
    }
}