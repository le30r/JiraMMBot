package team.microchad.model.repositories

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import team.microchad.model.entities.UserMap
import team.microchad.model.entities.UsersMap

class UserMapRepository : CrudRepository<UserMap, String> {
    override suspend fun findAll(): List<UserMap> = dbQuery {
        UsersMap.selectAll().map(::mapRowToUserMap)
    }

    override suspend fun findById(primaryKey: String): UserMap? = dbQuery {
        UsersMap.select(UsersMap.mmUsername eq primaryKey).map(::mapRowToUserMap).firstOrNull()
    }

    suspend fun  findByMmUsername(mmUsername: String): UserMap? = dbQuery {
        UsersMap.select(UsersMap.mmUsername eq mmUsername).map(::mapRowToUserMap).firstOrNull()
    }

    override suspend fun delete(primaryKey: String): Boolean = dbQuery {
        UsersMap.deleteWhere { UsersMap.mmUsername eq primaryKey } > 0
    }

    override suspend fun update(primaryKey: String, entity: UserMap): Boolean = dbQuery {
        UsersMap.update({ UsersMap.mmUsername eq primaryKey }) {
            it[mmUsername] = entity.mmUsername
            it[jiraUsername] = entity.jiraUsername
        } > 0
    }

    override suspend fun create(entity: UserMap): UserMap? = dbQuery {
        val statement = UsersMap.insert {
            it[mmUsername] = entity.mmUsername
            it[jiraUsername] = entity.jiraUsername
        }
        statement.resultedValues?.firstOrNull()?.let(::mapRowToUserMap)
    }

    private fun mapRowToUserMap(row: ResultRow) = UserMap(
        mmUsername = row[UsersMap.mmUsername],
        jiraUsername = row[UsersMap.jiraUsername]
    )
}