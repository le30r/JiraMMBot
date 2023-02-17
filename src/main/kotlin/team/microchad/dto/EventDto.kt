package team.microchad.dto

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import team.microchad.config.JiraLocalDateTimeDeserializer
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

enum class WebHookEvent {
    @JsonProperty("comment_created")
    COMMENT_CREATED,
    @JsonProperty("comment_updated")
    COMMENT_UPDATED,
    @JsonProperty("comment_deleted")
    COMMENT_DELETED,
    @JsonProperty("jira:issue_updated")
    JIRA_ISSUE_UPDATED,
    @JsonProperty("jira:issue_created")
    JIRA_ISSUE_CREATED,
    @JsonProperty("jira:issue_deleted")
    JIRA_ISSUE_DELETED;

    companion object {
        fun issueEvents(): Array<WebHookEvent> {
            return arrayOf(
                WebHookEvent.JIRA_ISSUE_UPDATED,
                WebHookEvent.JIRA_ISSUE_CREATED,
                WebHookEvent.JIRA_ISSUE_DELETED
            )
        }
    }

    fun isIssueEvent(): Boolean {
        return this in issueEvents()
    }

}

enum class IssueEventTypeName {
    @JsonProperty("issue_commented")
    ISSUE_COMMENTED,

    @JsonProperty("issue_created")
    ISSUE_CREATED,

    @JsonProperty("issue_generic")
    ISSUE_GENERIC,

    @JsonProperty("issue_updated")
    ISSUE_UPDATED,

    @JsonProperty("issue_comment_edited")
    ISSUE_COMMENT_EDITED,

    @JsonProperty("issue_comment_deleted")
    ISSUE_COMMENT_DELETED,

    @JsonProperty("issue_assigned")
    ISSUE_ASSIGNED,

    @JsonProperty("issue_reopened")
    ISSUE_REOPENED
}

data class Event(
    val webhookEvent: WebHookEvent,

    @JsonAlias("issue_event_type_name")
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

    @JsonAlias("toString")
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

data class User(
    val name: String,

    val displayName: String
)

data class Comment(
    val body: String,

    val author: User?,

    @field:JsonDeserialize(using = JiraLocalDateTimeDeserializer::class)
    val created: LocalDateTime,

    val updateAuthor: User?,

    val self: String,

    @field:JsonDeserialize(using = JiraLocalDateTimeDeserializer::class)
    val updated: LocalDateTime?
)

data class Project(
    val id: Long,

    val self: String,

    val key: String,

    val name: String
)

data class JiraComponent(
    val self: String,

    val name: String
)

data class Attachment(
    val filename: String,

    val content: String
)

data class Version(
    val id: Long,

    val self: String,

    val description: String?,

    val name: String,

    val archived: Boolean,

    val released: Boolean
)

data class Status(
    val id: String,

    val description: String,

    val name: String
)

data class Priority(
    val name: String,

    val iconUrl: String
)