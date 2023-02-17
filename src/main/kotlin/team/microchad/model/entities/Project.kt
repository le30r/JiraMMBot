package team.microchad.model.entities

import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.Table

data class Project(val id: Long, val name: String)

object Projects : Table("projects") {

    val id = long("id").autoIncrement()
    val name = varchar("name", 128)

    override val primaryKey = PrimaryKey(id)
}


