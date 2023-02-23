package team.microchad.model.entities

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import team.microchad.model.entities.Projects.autoIncrement
import java.util.concurrent.locks.LockSupport

data class Issue(
    val id: Long,
    val self: String,
    val key: String,
    val project: Long)

object Issues : Table("projects") {

    val id = long("id").autoIncrement()
    val self = varchar("self", 128)
    val key = varchar("key", 128)
    val project = long("project")
        .references(Projects.id)

    override val primaryKey = PrimaryKey(id)
}
