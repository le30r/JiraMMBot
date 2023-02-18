package team.microchad.dto.jira

@kotlinx.serialization.Serializable
data class Issue(
    var expand: String,
    var id: String,
    var self: String,
    var key: String,
    var fields: List<Field>
) {
}