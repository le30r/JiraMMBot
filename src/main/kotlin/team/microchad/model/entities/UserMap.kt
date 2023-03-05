package team.microchad.model.entities

import org.jetbrains.exposed.sql.Table

data class UserMap(
    var id: Long?,
    var mmUsername: String,
    var jiraUsername: String
)

object UsersMap : Table("user_map") {

    val id = long("id").autoIncrement()
    val jiraUsername = varchar("jira_username", 256)
    val mmUsername = varchar("mm_username", 256).uniqueIndex()

    override val primaryKey = PrimaryKey(id, jiraUsername, name = "pk_user_map")
}