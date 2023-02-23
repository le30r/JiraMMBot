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

class MmClient {
    companion object {
        private const val MM_BASE_WEBHOOK = "tin-workshop.ddns.net:8065/hooks"
        private const val MM_WEBHOOK_TOKEN = "5mj4ntowb7n3ddkb8hqbuw8sde"
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

    suspend fun send(msg: OutgoingMsg): HttpResponse = client.request {
        url{
            protocol = URLProtocol.HTTP
            host = MM_BASE_WEBHOOK
            appendPathSegments(MM_WEBHOOK_TOKEN)
        }
        method = HttpMethod.Post
        headers {
            append(HttpHeaders.ContentType, ContentType.Application.Json)
        }
        setBody(msg)
    }
}
