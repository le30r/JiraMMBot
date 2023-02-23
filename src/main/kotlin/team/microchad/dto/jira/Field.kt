package team.microchad.dto.jira

@kotlinx.serialization.Serializable
data class Field(
    var summary: String,
    var updated: String, //TODO( исправить представление даты)
)