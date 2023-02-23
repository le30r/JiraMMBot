package team.microchad.model.repositories

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import team.microchad.model.entities.Issues
import team.microchad.model.entities.User
import team.microchad.model.entities.Users

class UserRepository : CrudRepository<User> {
    override suspend fun findAll(): List<User> = dbQuery {
        Users.selectAll().map(::mapRowToIssue)
    }

    override suspend fun findById(id: Long): User? = dbQuery {
        Users.select(Users.id eq id).map(::mapRowToIssue).firstOrNull()
    }

    override suspend fun delete(id: Long): Boolean = dbQuery {
        Users.deleteWhere { Users.id eq id } > 0
    }

    override suspend fun update(id: Long, entity: User): Boolean = dbQuery {
        Users.update({ Users.id eq id }) {
            it[username] = entity.username
        } > 0
    }

    override suspend fun create(entity: User): User? = dbQuery {
        val statement = Users.insert {
            it[username] = entity.username
        }
        statement.resultedValues?.firstOrNull()?.let(::mapRowToIssue)
    }

    private fun mapRowToIssue(row: ResultRow) = User(
        id = row[Users.id],
        username = row[Users.username]
    )
}