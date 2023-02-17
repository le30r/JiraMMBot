package team.microchad.dto

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import team.microchad.config.JiraLocalDateTimeDeserializer
import java.time.LocalDateTime

data class JiraIssue(
    val id: Long,
    val self: String,
    val key: String,
    val fields: IssueFields
) {
    val versionsAsString: String
        get() {
            return fields.fixVersions.joinToString { it.name }
        }
    val labelsAsString: String
        get() {
            return fields.labels.joinToString()
        }
    val componentsAsString: String
        get() {
            return fields.components.joinToString { it.name }
        }
    val creatorName: String
        get() {
            return fields.creator.name
        }
    val assigneeName: String?
        get() {
            return fields.assignee?.name
        }
    val reporterName: String?
        get() {
            return fields.reporter?.name
        }
    val creatorDisplayName: String
        get() {
            return fields.creator.displayName
        }
    val assigneeDisplayName: String?
        get() {
            return fields.assignee?.displayName
        }
    val reporterDisplayName: String?
        get() {
            return fields.reporter?.displayName
        }
    val containsVersions: Boolean
        get() {
            return fields.fixVersions.isNotEmpty()
        }
    val containsLabels: Boolean
        get() {
            return fields.labels.isNotEmpty()
        }
    val containsAttachments: Boolean
        get() {
            return fields.attachment.isNotEmpty()
        }

}

data class IssueFields(
    val summary: String,

    val description: String?,

    val project: Project?,

    val creator: User,

    val issueType: IssueType,

    val fixVersions: Array<Version> = emptyArray(),

    val attachment: Array<Attachment> = emptyArray(),

    @field:JsonDeserialize(using = JiraLocalDateTimeDeserializer::class)
    val created: LocalDateTime,

    val reporter: User?,

    val assignee: User?,

    @field:JsonDeserialize(using = JiraLocalDateTimeDeserializer::class)
    val updated: LocalDateTime?,

    val status: Status,

    val priority: Priority,

    val components: Array<JiraComponent> = emptyArray(),

    val labels: Array<String> = emptyArray(),

    val watches: Watchers?
)
data class IssueType(
    val name: String,
    val description: String
)

data class Watchers(
    val self: String,
    val watchCount: Int,
    val isWatching: Boolean
)
