package team.microchad.client

import io.ktor.client.*
import io.ktor.client.engine.java.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

import kotlinx.serialization.json.Json

import team.microchad.config.MattermostConfiguration

import team.microchad.dto.mm.OutgoingMsg

class MmClient {
    private val configuration = MattermostConfiguration()

    private val client = HttpClient(Java) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun send(message: OutgoingMsg): HttpResponse = client.request {
        url {
            protocol = URLProtocol.HTTP
            host = configuration.webhookUrl
            appendPathSegments(configuration.token)
        }
        method = HttpMethod.Post
        headers {
            append(HttpHeaders.ContentType, ContentType.Application.Json)
        }
        setBody(message)
    }
}
