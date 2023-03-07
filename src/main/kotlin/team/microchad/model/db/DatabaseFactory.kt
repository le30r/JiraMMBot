package team.microchad.model.db

import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import team.microchad.model.entities.ProjectsMap
import team.microchad.model.entities.UsersMap

object DatabaseFactory {

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}

fun Application.dbInit() {
    val database = Database.connect(
        "jdbc:postgresql://localhost:5432/mmbot",
        "org.postgresql.Driver",
        "postgres",
        "0000"
    )
    transaction(database) {
        SchemaUtils.create(UsersMap)
        SchemaUtils.create(ProjectsMap)
    }
}