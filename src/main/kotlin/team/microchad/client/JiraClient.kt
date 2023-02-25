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

import team.microchad.dto.jira.JiraJqlResponse

import kotlinx.serialization.json.Json

import team.microchad.config.JiraConfiguration
import team.microchad.dto.jira.Comment
import team.microchad.exceptions.JiraBadRequestException


class JiraClient {

    private val configuration = JiraConfiguration()

    private val client = HttpClient(Java) {
        install(Auth) {
            basic {
                sendWithoutRequest { true }
                credentials {
                    BasicAuthCredentials(configuration.botUsername, configuration.botPassword)
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

    //TODO method to just send a jql. Create a jqlFactory
    suspend fun getByJql(jql: String): JiraJqlResponse {
        val response: HttpResponse = client.get {
            url {
                protocol = URLProtocol.HTTP
                host = configuration.baseUrl
                appendPathSegments(configuration.apiPath)
                encodedParameters.append("jql", jql)
                trailingQuery = true
            }
        }
        if (response.status == HttpStatusCode.OK)
            return response.body()
        else
            throw JiraBadRequestException("Jira return ${response.status}. Check if the request is correct.")
    }

    suspend fun getProjectsByUser(username: String): JiraJqlResponse {
        return getByJql("assignee=$username")
    }

    suspend fun commentIssue(issueKey: String, comment: String): Boolean {
        val response: HttpResponse = client.post {
            url {
                protocol = URLProtocol.HTTP
                host = configuration.baseUrl
                appendPathSegments(configuration.apiComment.replace("?", issueKey))//TODO Implement parameter injection instead of ?
                setBody(Comment(body = comment, visibility = null))
                trailingQuery = true
            }
        }
        return response.status == HttpStatusCode.OK
    }



    private fun jqlQueryFor(username: String, status: String) =
       String(("assignee=${username}%20and%20status=$status&fields=id,key,summary,updated").toByteArray(), Charsets.UTF_8)
           .replace(" ","%20")
           .replace("\"", "%22")

}
