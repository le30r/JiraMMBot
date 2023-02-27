package team.microchad.client

import com.atlassian.jira.jql.field.Assignee
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

import team.microchad.dto.jira.JiraJqlResponse
import team.microchad.config.JiraConfiguration
import team.microchad.dto.jira.Comment
import team.microchad.dto.jira.Status
import team.microchad.dto.jira.User
import team.microchad.exceptions.JiraBadRequestException
import team.microchad.utils.toUrl


class JiraClient : KoinComponent {

    private val configuration: JiraConfiguration by inject()
    val jqlQuery = Assignee equalTo "mrsaloed"
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
                appendPathSegments(configuration.apiPath, configuration.searchJql)
                encodedParameters.append("jql", jql)
                trailingQuery = true
            }
        }
        if (response.status == HttpStatusCode.OK)
            return response.body()
        else
            throw JiraBadRequestException("Jira return ${response.status}. Check if the request is correct.")
    }

    suspend fun getStatuses(): Array<Status> {
        val response: HttpResponse = client.get {
            url {
                protocol = URLProtocol.HTTP
                host = configuration.baseUrl
                appendPathSegments(configuration.apiPath, configuration.status)
                trailingQuery = true
            }
            headers {
                contentType(ContentType.Application.Json)
            }
        }
        if (response.status == HttpStatusCode.OK)
            return response.body()
        else
            throw JiraBadRequestException("Jira return ${response.status}. Check if the request is correct.")
    }

    suspend fun getUsers(): Array<User> {
        val response: HttpResponse = client.get {
            url {
                protocol = URLProtocol.HTTP
                host = configuration.baseUrl
                appendPathSegments(configuration.apiPath, "user/search")
                encodedParameters.append("username", ".")
                trailingQuery = true
            }
            headers {
                contentType(ContentType.Application.Json)
            }
        }
        if (response.status == HttpStatusCode.OK)
            return response.body()
        else
            throw JiraBadRequestException("Jira return ${response.status}. Check if the request is correct.")
    }

    suspend fun commentIssue(issueKey: String, newComment: String): Boolean {
        val response: HttpResponse = client.post {
            url {
                protocol = URLProtocol.HTTP
                host = configuration.baseUrl
                with(configuration){
                    appendPathSegments(apiPath, issue, issueKey, comment)//TODO Implement parameter injection instead of ?
                }
                setBody(Comment(body = newComment, visibility = null))
                contentType(ContentType.Application.Json)
            }
        }
        return response.status == HttpStatusCode.Created
    }

    //TODO use "space" and toUrl instead of unicode symbols
    suspend fun getOutstandingIssuesForUser(username: String): JiraJqlResponse {
        return getByJql("assignee=$username%20and%20status!=%22Done%22")
    }

    suspend fun getUserIssuesSortedByStatus(username: String): JiraJqlResponse {
        return getByJql("assignee=$username%20ORDER%20BY%20status")
    }

    suspend fun getUserIssuesWithStatus(username: String, status: String): JiraJqlResponse {
        return getByJql("assignee=$username%20and%20status=%22$status%22")
    }

    suspend fun getDoneIssuesForDays(days: Int): JiraJqlResponse {
        return getByJql("status%20changed%20to%20%22Done%22%20AFTER%20-${days}d")
    }

    suspend fun getUserDoneIssuesForDays(username: String,days: Int): JiraJqlResponse {
        return getByJql("assignee=$username%20and%20status%20changed%20to%20%22Done%22%20AFTER%20-${days}d")
    }

    suspend fun createIssue() {

    }

    private fun jqlQueryFor(username: String, status: String) =
       String(("assignee=${username}%20and%20status=$status&fields=id,key,summary,updated").toByteArray(), Charsets.UTF_8)
           .replace(" ","%20")
           .replace("\"", "%22")

}
