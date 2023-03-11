package team.microchad.model.entities

import org.jetbrains.exposed.sql.Table

data class ProjectMap(
    var project: String,
    var chat: String,
    var monday: Boolean,
    var friday: Boolean,
    var everyday: Boolean
)

object ProjectsMap : Table("project_map") {

    val project = varchar("project", 256)
    val chat = varchar("chat", 256)
    val monday = bool("monday")
    val friday = bool("friday")
    val everyday = bool("everyday")

    override val primaryKey = PrimaryKey(chat, name = "pk_project_chat_map")
}