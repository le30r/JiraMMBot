package team.microchad.model.repositories

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

interface CrudRepository<T, E> {
    suspend fun findAll(): List<T>
    suspend fun findById(primaryKey: E): T?
    suspend fun create(entity: T): T?
    suspend fun update(primaryKey: E, entity: T): Boolean
    suspend fun delete(primaryKey: E): Boolean

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}