package team.microchad.client

import io.ktor.client.*
import io.ktor.client.engine.java.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.net.URI
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.utils.URIBuilder
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils

class JiraClient(_username: String, _password: String) {
    companion object {
        private const val JIRA_API_BASE_URL = "tin-workshop.ddns.net:8080/rest/agile/1.0"
        private const val JIRA_ISSUE_ENDPOINT = "issue"
    }
    private val botUsername: String = _username
    private val botPassword: String = _password

    private val client = HttpClient(Java) {
        install(Auth) {
            basic {
                sendWithoutRequest { true }
                credentials {
                    BasicAuthCredentials(username = botUsername, password = _password)
                }
            }
        }
    }

    suspend fun getIssue(issueKey: String): String {

        val response: HttpResponse = client.get() {
            url {
                headers {
                    append("JSESSIONID", "41884CDB3D7D01938EAB4C16BFB9B768")
                }
                protocol = URLProtocol.HTTP
                host = JIRA_API_BASE_URL
                appendPathSegments(JIRA_ISSUE_ENDPOINT, issueKey)
            }
        }
        return response.bodyAsText()
    }
}