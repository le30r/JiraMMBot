package team.microchad.model.repositories

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import team.microchad.model.entities.ProjectMap
import team.microchad.model.entities.ProjectsMap

class ProjectMapRepository : CrudRepository<ProjectMap> {
    override suspend fun findAll(): List<ProjectMap> = dbQuery {
        ProjectsMap.selectAll().map(::mapRowToProjectMap)
    }

    override suspend fun findById(id: Long): ProjectMap? = dbQuery {
        ProjectsMap.select(ProjectsMap.id eq id).map(::mapRowToProjectMap).firstOrNull();
    }

    override suspend fun delete(id: Long): Boolean = dbQuery {
        ProjectsMap.deleteWhere { ProjectsMap.id eq id } > 0
    }

    override suspend fun update(id: Long, entity: ProjectMap): Boolean = dbQuery {
        ProjectsMap.update({ ProjectsMap.id eq id }) {
            it[project] = entity.project
            it[chat] = entity.chat
        } > 0
    }

    override suspend fun create(entity: ProjectMap): ProjectMap? = dbQuery {
        val statement = ProjectsMap.insert {
            it[project] = entity.project
            it[chat] = entity.chat
        }
        statement.resultedValues?.firstOrNull()?.let(::mapRowToProjectMap)
    }

    private fun mapRowToProjectMap(row: ResultRow) = ProjectMap(
        id = row[ProjectsMap.id],
        project = row[ProjectsMap.project],
        chat = row[ProjectsMap.chat]
    )

}