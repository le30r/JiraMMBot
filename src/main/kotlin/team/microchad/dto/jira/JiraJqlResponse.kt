package team.microchad.dto.jira

@kotlinx.serialization.Serializable
data class JiraJqlResponse(
    var expand: String,
    var startAt : Long,
    var maxResults: Long,
    var total: Long,
    var issues: List<Issue>
) {
}