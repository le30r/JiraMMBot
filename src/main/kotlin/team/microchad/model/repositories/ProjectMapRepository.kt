package team.microchad.model.repositories

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.postgresql.util.PSQLException
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
            it[monday] = entity.monday
            it[friday] = entity.friday
            it[everyday] = entity.everyday
        } > 0
    }

    private suspend fun setProject(entity: ProjectMap): Boolean = dbQuery {
        ProjectsMap.update({ ProjectsMap.chat eq entity.chat }) {
            it[project] = entity.project
            it[chat] = entity.chat
        } > 0
    }

    suspend fun mapProjectAndChat(entity: ProjectMap): Boolean = dbQuery {
        if (ProjectsMap.select(ProjectsMap.chat eq entity.chat).none()) {
            create(entity)
        } else {
            setProject(entity)
        }
    }

    override suspend fun create(entity: ProjectMap): Boolean = dbQuery {
        ProjectsMap.insert {
            it[project] = entity.project
            it[chat] = entity.chat
            it[monday] = entity.monday
            it[friday] = entity.friday
            it[everyday] = entity.everyday
        }
        return@dbQuery true
    }

    private fun mapRowToProjectMap(row: ResultRow) = ProjectMap(
        project = row[ProjectsMap.project],
        chat = row[ProjectsMap.chat],
        monday = row[ProjectsMap.monday],
        friday = row[ProjectsMap.friday],
        everyday = row[ProjectsMap.everyday]
    )

}