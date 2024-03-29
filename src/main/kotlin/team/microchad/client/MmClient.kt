package team.microchad.client

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.java.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

import kotlinx.serialization.json.Json

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

import team.microchad.config.MattermostConfiguration
import team.microchad.dto.mm.DirectChannel
import team.microchad.dto.mm.OutgoingMsg
import team.microchad.dto.mm.dialog.DialogMessage

class MmClient : KoinComponent {

    private val configuration: MattermostConfiguration by inject()

    private val client = HttpClient(Java) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(Auth) {
            bearer {
                sendWithoutRequest { true }
                loadTokens {
                    BearerTokens(configuration.token, "")
                }
            }
        }
    }

    suspend fun send(message: OutgoingMsg): HttpResponse = client.request {
        url {
            protocol = URLProtocol.HTTP
            host = configuration.webhookUrl
            appendPathSegments(configuration.token)
        }
        contentType(ContentType.Application.Json)
        method = HttpMethod.Post
        setBody(message)
    }


    suspend fun createDirectChannel(userId: String): String = client.request {
        url {
            protocol = URLProtocol.HTTP
            host = configuration.api
        }
        contentType(ContentType.Application.Json)
        method = HttpMethod.Post
        setBody(
            listOf(configuration.botId, userId)
        )
    }.body<DirectChannel>().id

    suspend fun sendToDirectChannel(message: OutgoingMsg): HttpResponse = client.request {
        url {
            protocol = URLProtocol.HTTP
            host = configuration.posts
        }
        contentType(ContentType.Application.Json)
        method = HttpMethod.Post
        setBody(message)
    }

    suspend fun openDialog(message: DialogMessage): HttpResponse = client.request {
        url {
            protocol = URLProtocol.HTTP
            host = configuration.openDialog
        }
        contentType(ContentType.Application.Json)
        method = HttpMethod.Post
        setBody(message)
    }

}
