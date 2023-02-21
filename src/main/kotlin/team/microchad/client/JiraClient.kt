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
import team.microchad.dto.jira.JiraJqlResponse

class JiraClient(_username: String, _password: String) {
    companion object {
        private const val JIRA_API_BASE_URL = "tin-workshop.ddns.net:8080"
        private const val JIRA_API_PATH = "rest/api/2/search"
        private const val JIRA_JQL = "jql="
    }

    private val botUsername: String = _username
    private val botPassword: String = _password

    private val client = HttpClient(Java) {
        install(Auth) {
            basic {
                sendWithoutRequest { true }
                credentials {
                    BasicAuthCredentials(username = botUsername, password = botPassword)
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

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

    }

    suspend fun getIssue(issueKey: String): String {

        val response: HttpResponse = client.get {
            url {
                protocol = URLProtocol.HTTP
                host = JIRA_API_BASE_URL
                appendPathSegments(JIRA_API_PATH, JIRA_JQL, issueKey)
            }
        }
        return response.bodyAsText()
    }

    suspend fun getIssues(username: String, status: String): JiraJqlResponse {
        val response: HttpResponse = client.get {
            url {
                protocol = URLProtocol.HTTP
                host = JIRA_API_BASE_URL
                appendPathSegments(JIRA_API_PATH)
                encodedParameters.append("jql", jqlQueryFor(username, status))
                trailingQuery = true
            }
        }
        return response.body()
    }

    private fun jqlQueryFor(username: String, status: String) =
        String(("assignee=$username&fields=id,key,summary,updated&status=$status").toByteArray(), Charsets.UTF_8)


}