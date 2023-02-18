package team.microchad.model.repositories

import org.jetbrains.exposed.sql.*
import team.microchad.model.entities.Project
import team.microchad.model.entities.Projects

class ProjectRepository : CrudRepository<Project> {
    override suspend fun findAll(): List<Project> = dbQuery {
        Projects.selectAll().map(::mapRowToProject)
    }

    override suspend fun findById(id: Long): Project? = dbQuery {
        Projects
            .select { Projects.id eq id }
            .map(::mapRowToProject)
            .firstOrNull()
    }

    override suspend fun create(entity: Project): Project? = dbQuery {
        val statement = Projects.insert {
            it[name] = entity.name
        }
        statement.resultedValues?.firstOrNull()?.let(::mapRowToProject)
    }

    override suspend fun update(id: Long, entity: Project): Boolean = dbQuery {
        Projects.update({ Projects.id eq id }) { it[name] = entity.name } > 0
    }

    override suspend fun delete(id: Long): Boolean = dbQuery {
        Projects.deleteWhere { Projects.id eq id } > 0
    }

    private fun mapRowToProject(row: ResultRow) = Project(id = row[Projects.id], name = row[Projects.name])

}
