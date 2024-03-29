package team.microchad

import io.ktor.server.application.*
import io.ktor.server.config.*
import team.microchad.model.db.dbInit
import team.microchad.plugins.*
import team.microchad.service.scheduler.configureScheduler
import java.util.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)


@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    getSecrets()
    configureDI()
    configureRouting()
    configureSerialization()
    dbInit()
    configureScheduler()
}
