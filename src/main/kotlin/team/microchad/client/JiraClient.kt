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
import team.microchad.config.JiraConfiguration
import team.microchad.dto.jira.*
import team.microchad.exceptions.JiraBadRequestException


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

    suspend fun createIssue(issueDto: Issue): Boolean {
        val response: HttpResponse = client.post {
            url {
                protocol = URLProtocol.HTTP
                host = configuration.baseUrl
                with(configuration){
                    appendPathSegments(apiPath, issue)//TODO Implement parameter injection instead of ?
                }
                setBody(Issue)
                contentType(ContentType.Application.Json)
            }
        }
        return response.status == HttpStatusCode.Created
    }

    suspend fun updateIssue(issueDto: Issue): Boolean {
        val response: HttpResponse = client.put {
            url {
                protocol = URLProtocol.HTTP
                host = configuration.baseUrl
                with(configuration) {
                    appendPathSegments(apiPath, issue, issueDto.key)//TODO Implement parameter injection instead of ?
                }
                setBody(Issue)
                contentType(ContentType.Application.Json)
            }
        }
        return response.status == HttpStatusCode.NoContent
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

    private fun jqlQueryFor(username: String, status: String) =
       String(("assignee=${username}%20and%20status=$status&fields=id,key,summary,updated").toByteArray(), Charsets.UTF_8)
           .replace(" ","%20")
           .replace("\"", "%22")

}

