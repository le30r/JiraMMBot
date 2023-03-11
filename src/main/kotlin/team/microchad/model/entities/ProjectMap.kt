package team.microchad.model.entities

import org.jetbrains.exposed.sql.Table

data class ProjectMap(
    var id: Long? = null,
    var project: String,
    var chat: String
)

object ProjectsMap : Table("project_map") {

    val id = long("id").autoIncrement()
    val project = varchar("project", 256)
    val chat = varchar("chat", 256)

    override val primaryKey = PrimaryKey(id, name = "pk_project_chat_map")
}