package team.microchad.dto.jira

import kotlinx.serialization.*
import kotlinx.serialization.json.JsonNames

@Serializable
data class JiraJqlResponse(
    var expand: String?,
    var startAt: Long,
    var maxResults: Long,
    var total: Long,
    @JsonNames("issues")
    var issues: Array<Issue>
)