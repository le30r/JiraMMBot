package team.microchad.dto.jira

import kotlinx.serialization.*

@Serializable
data class Field(
    var summary: String,
    var updated: String, //TODO( исправить представление даты)
)