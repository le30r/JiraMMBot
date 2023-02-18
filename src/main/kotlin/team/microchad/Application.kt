package team.microchad

import team.microchad.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.config.*
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import team.microchad.client.JiraClient
import team.microchad.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)


@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    getSecrets()
    configureRouting()
    configureSerialization()
    configDatabase()
    configureDI()
}





