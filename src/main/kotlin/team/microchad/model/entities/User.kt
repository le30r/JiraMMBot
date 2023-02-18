package team.microchad.model.entities

import org.jetbrains.exposed.sql.Table

data class User(
    var id: Long,
    var username: String
)

object Users : Table("projects") {

    val id = long("id").autoIncrement()
    val username = varchar("self", 256)

    override val primaryKey = PrimaryKey(id)
}