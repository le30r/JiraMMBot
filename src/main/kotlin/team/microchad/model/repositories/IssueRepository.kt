package team.microchad.model.repositories

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import team.microchad.model.entities.Issue
import team.microchad.model.entities.Issues

class IssueRepository : CrudRepository<Issue> {
    override suspend fun findAll(): List<Issue> = dbQuery {
        Issues.selectAll().map(::mapRowToIssue)
    }

    override suspend fun findById(id: Long): Issue? = dbQuery {
        Issues.select(Issues.id eq id).map(::mapRowToIssue).firstOrNull()
    }

    override suspend fun create(entity: Issue): Issue? = dbQuery {
        val statement = Issues.insert {
            it[self] = entity.self
            it[key] = entity.key
            it[project] = entity.project
        }
        statement.resultedValues?.firstOrNull()?.let(::mapRowToIssue)
    }

    override suspend fun update(id: Long, entity: Issue): Boolean = dbQuery {
        Issues.update({ Issues.id eq id }) {
            it[self] = entity.self
            it[key] = entity.key
            it[project] = entity.project
        } > 0
    }

    override suspend fun delete(id: Long): Boolean = dbQuery {
        Issues.deleteWhere { Issues.id eq id } > 0
    }


    private fun mapRowToIssue(row: ResultRow) = Issue(
        id = row[Issues.id],
        self = row[Issues.self],
        key = row[Issues.key],
        project = row[Issues.project]
    )

}