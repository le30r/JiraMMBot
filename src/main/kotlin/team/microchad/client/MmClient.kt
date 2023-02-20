package team.microchad.client

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.java.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import team.microchad.dto.mm.User


class MmClient(private val _accessToken: String) {
    companion object {
        const val MM_API_BASE_URL: String = "tin-workshop.ddns.net:8065"
        const val MM_API_VERSION: String = "api/v4"
        const val MM_USERS: String = "users"
    }
    private val client = HttpClient(Java) {
        install(Auth) {
            bearer {
                sendWithoutRequest { true }
                loadTokens {
                    BearerTokens(_accessToken, "")
                }
            }
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }


    }

    suspend fun getUsers(): User {
        return client.get {
            url {
                protocol = URLProtocol.HTTP
                host = MM_API_BASE_URL
                appendPathSegments(MM_API_VERSION, MM_USERS, "me")
            }
        }.body()
    }

}