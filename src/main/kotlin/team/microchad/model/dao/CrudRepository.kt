package team.microchad.model.dao

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

interface CrudRepository<T> {
    suspend fun findAll(): List<T>
    suspend fun findById(id: Long): T?
    suspend fun create(name: String): T?
    suspend fun update(id: Long, name: String): Boolean
    suspend fun delete(id: Long): Boolean

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}