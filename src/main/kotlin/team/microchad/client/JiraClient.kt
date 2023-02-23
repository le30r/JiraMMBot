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

import team.microchad.plugins.Secrets
import team.microchad.dto.jira.JiraJqlResponse

import kotlinx.serialization.json.Json
import team.microchad.exceptions.JiraBadRequestException


class JiraClient {

    //TODO Remove this and use DI. For example create a JiraConfiguration class
    companion object {
        private const val JIRA_API_BASE_URL = "tin-workshop.ddns.net:8080"
        private const val JIRA_API_PATH = "rest/api/2/search"
        private const val JIRA_JQL = "jql="
    }

    private val botUsername: String = Secrets.botUsername
    private val botPassword: String = Secrets.botPassword

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

    }

    //TODO method to just send a jql. На подумать: Может вернуть как нормальный ответ, так и ошибку. Учесть это
    suspend fun sendJql(jql: String): JiraJqlResponse {
        val response: HttpResponse = client.get {
            url {
                protocol = URLProtocol.HTTP
                host = JIRA_API_BASE_URL
                appendPathSegments(JIRA_API_PATH)
                encodedParameters.append("jql", jql)
                trailingQuery = true
            }
        }
        if (response.status == HttpStatusCode.OK)
            return response.body()
        else
            throw JiraBadRequestException("Jira return ${response.status}. Check if the request is correct.")
    }

    //TODO Remove this and use generic. For example sendJql()
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

    //TODO Remove this and use generic. For example sendJql()
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

    //TODO Remove this and use generic. For example sendJql()
    private fun jqlQueryFor(username: String, status: String) =
       String(("assignee=${username}%20and%20status=$status&fields=id,key,summary,updated").toByteArray(), Charsets.UTF_8)
           .replace(" ","%20")
           .replace("\"", "%22")

}
