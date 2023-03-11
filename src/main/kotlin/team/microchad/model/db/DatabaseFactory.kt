package team.microchad.model.db

import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import team.microchad.model.entities.ProjectsMap
import team.microchad.model.entities.UsersMap
import team.microchad.plugins.Secrets

object DatabaseFactory {

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}

fun Application.dbInit() {

    val database = with(Secrets) {
        Database.connect(
            "jdbc:postgresql://$dbHost:$dbPort/$dbName",
            "org.postgresql.Driver",
            dbLogin,
            dbPassword
        )
    }
    transaction(database) {
        SchemaUtils.create(UsersMap)
        SchemaUtils.create(ProjectsMap)
    }
}

