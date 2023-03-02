package team.microchad.dto

import kotlinx.serialization.SerialName

import team.microchad.utils.JiraLocalDateTimeDeserializer

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId


//TODO Refactor!
enum class WebHookEvent {
    @SerialName("comment_created")
    COMMENT_CREATED,
    @SerialName("comment_updated")
    COMMENT_UPDATED,
    @SerialName("comment_deleted")
    COMMENT_DELETED,
    @SerialName("jira:issue_updated")
    JIRA_ISSUE_UPDATED,
    @SerialName("jira:issue_created")
    JIRA_ISSUE_CREATED,
    @SerialName("jira:issue_deleted")
    JIRA_ISSUE_DELETED;

    companion object {
        fun issueEvents(): Array<WebHookEvent> {
            return arrayOf(
                JIRA_ISSUE_UPDATED,
                JIRA_ISSUE_CREATED,
                JIRA_ISSUE_DELETED
            )
        }
    }

    fun isIssueEvent(): Boolean {
        return this in issueEvents()
    }

}

enum class IssueEventTypeName {
    @SerialName("issue_commented")
    ISSUE_COMMENTED,

    @SerialName("issue_created")
    ISSUE_CREATED,

    @SerialName("issue_generic")
    ISSUE_GENERIC,

    @SerialName("issue_updated")
    ISSUE_UPDATED,

    @SerialName("issue_comment_edited")
    ISSUE_COMMENT_EDITED,

    @SerialName("issue_comment_deleted")
    ISSUE_COMMENT_DELETED,

    @SerialName("issue_assigned")
    ISSUE_ASSIGNED,

    @SerialName("issue_reopened")
    ISSUE_REOPENED
}

data class Event(
    val webhookEvent: WebHookEvent,

    @SerialName("issue_event_type_name")
    val issueEventTypeName: IssueEventTypeName?,

    val timestamp: Long,

    val user: User?,

    val issue: JiraIssue?,

    val comment: Comment?,

    val changelog: Changelog?
) {
    val eventDate: LocalDateTime

    init {
        eventDate = timestampAsDate()
    }

    val projectName: String
        get() {
            return issue?.fields?.project?.name ?: ""
        }

    val isIssueEvent: Boolean
        get() {
            return webhookEvent.isIssueEvent()
        }

    private fun timestampAsDate(): LocalDateTime {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
    }


}

data class Changelog(
    val id: String,

    val items: Array<ChangelogItem> = emptyArray()
)

data class ChangelogItem(
    val field: String,

    val fromString: String?,

    @SerialName("toString")
    val newString: String?
) {
    val changed: Boolean
        get() {
            return fromString != null && newString != null
        }

    val added: Boolean
        get() {
            return fromString == null && newString != null
        }

    val removed: Boolean
        get() {
            return fromString != null && newString == null
        }

    val statusChanged: Boolean
        get() {
            return "status" == this.field
        }

    val assigneeChanged: Boolean
        get() {
            return "assignee" == this.field
        }
}

@kotlinx.serialization.Serializable
data class User(
    val name: String,

    val displayName: String
)

data class Comment(
    val body: String,

    val author: User?,

    //@field:JsonDeserialize(using = JiraLocalDateTimeDeserializer::class)
    @kotlinx.serialization.Serializable(with = JiraLocalDateTimeDeserializer::class)
    val created: LocalDateTime,

    val updateAuthor: User?,

    val self: String,

    val updated: LocalDateTime?
)

@kotlinx.serialization.Serializable
data class Project(
    val id: Long,

    val self: String,

    val key: String,

    val name: String
)

@kotlinx.serialization.Serializable
data class JiraComponent(
    val self: String,

    val name: String
)

@kotlinx.serialization.Serializable
data class Attachment(
    val filename: String,

    val content: String
)

@kotlinx.serialization.Serializable
data class Version(
    val id: Long,

    val self: String,

    val description: String?,

    val name: String,

    val archived: Boolean,

    val released: Boolean
)

@kotlinx.serialization.Serializable
data class Status(
    val id: String,

    val description: String,

    val name: String
)

@kotlinx.serialization.Serializable
data class Priority(
    val name: String,

    val iconUrl: String
)