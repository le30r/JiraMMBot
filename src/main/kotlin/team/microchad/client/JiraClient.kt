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

import team.microchad.config.JiraConfiguration
import team.microchad.dto.jira.*
import team.microchad.exceptions.JiraBadRequestException


class JiraClient : KoinComponent {

    private val configuration: JiraConfiguration by inject()

    suspend fun getByJql(jql: String): JiraJqlResponse {
        val params = Parameters.build {
            append("jql", jql)
        }
        val response = httpResponseByJiraWithEncodedParams(configuration.searchJql, params)
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
                with(configuration) {
                    appendPathSegments(apiPath, issue)
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
                    appendPathSegments(apiPath, issue, issueDto.key)
                }
                setBody(Issue)
                contentType(ContentType.Application.Json)
            }
        }
        return response.status == HttpStatusCode.NoContent
    }

    suspend fun commentIssue(issueKey: String, newComment: String): Boolean {
        val response: HttpResponse = client.post {
            url {
                protocol = URLProtocol.HTTP
                host = configuration.baseUrl
                with(configuration) {
                    appendPathSegments(
                        apiPath,
                        issue,
                        issueKey,
                        comment
                    )
                }
                setBody(Comment(body = newComment, visibility = null))
                contentType(ContentType.Application.Json)
            }
        }
        return response.status == HttpStatusCode.Created
    }

    suspend fun getStatuses(): Array<Status> {
        val response = httpResponseByJira(configuration.status)
        if (response.status == HttpStatusCode.OK)
            return response.body()
        else
            throw JiraBadRequestException("Jira return ${response.status}. Check if the request is correct.")
    }

    suspend fun getProjects(): Array<Project> {
        val response = httpResponseByJira(configuration.project)
        if (response.status == HttpStatusCode.OK)
            return response.body()
        else
            throw JiraBadRequestException("Jira return ${response.status}. Check if the request is correct.")
    }

    suspend fun getUsers(): Array<User> {
        val params = Parameters.build {
            append("username", ".")
        }
        val response = httpResponseByJiraWithEncodedParams("user/search", params)
        if (response.status == HttpStatusCode.OK)
            return response.body()
        else
            throw JiraBadRequestException("Jira return ${response.status}. Check if the request is correct.")
    }

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

    private suspend fun httpResponseByJira(endpoint: String): HttpResponse = client.get {
        url {
            protocol = URLProtocol.HTTP
            host = configuration.baseUrl
            appendPathSegments(configuration.apiPath, endpoint)
            trailingQuery = true
        }
        headers {
            contentType(ContentType.Application.Json)
        }
    }

    private suspend fun httpResponseByJiraWithEncodedParams(endpoint: String, params: Parameters) = client.get {
        url {
            protocol = URLProtocol.HTTP
            host = configuration.baseUrl
            appendPathSegments(configuration.apiPath, endpoint)
            encodedParameters.appendAll(params)
            trailingQuery = true
        }
        headers {
            contentType(ContentType.Application.Json)
        }
    }

}
