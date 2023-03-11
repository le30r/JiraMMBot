package team.microchad.model.repositories

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import team.microchad.model.entities.ProjectMap
import team.microchad.model.entities.ProjectsMap

class ProjectMapRepository : CrudRepository<ProjectMap, String> {
    override suspend fun findAll(): List<ProjectMap> = dbQuery {
        ProjectsMap.selectAll().map(::mapRowToProjectMap)
    }

    override suspend fun findById(primaryKey: String): ProjectMap? = dbQuery {
        ProjectsMap.select(ProjectsMap.chat eq primaryKey).map(::mapRowToProjectMap).firstOrNull();
    }

    override suspend fun delete(primaryKey: String): Boolean = dbQuery {
        ProjectsMap.deleteWhere { ProjectsMap.chat eq primaryKey } > 0
    }

    override suspend fun update(primaryKey: String, entity: ProjectMap): Boolean = dbQuery {
        ProjectsMap.update({ ProjectsMap.chat eq primaryKey }) {
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
        project = row[ProjectsMap.project],
        chat = row[ProjectsMap.chat],
        monday = row[ProjectsMap.monday],
        friday = row[ProjectsMap.friday],
        everyday = row[ProjectsMap.everyday]
    )

}