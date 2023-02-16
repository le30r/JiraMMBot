package team.microchad

import team.microchad.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.config.*
import kotlinx.coroutines.runBlocking
import team.microchad.client.JiraClient
import team.microchad.client.MmClient
import team.microchad.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)


@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureRouting()
    configureSerialization()
    val botUsername: String = environment.config.tryGetString("bot.auth.username") ?: ""
    val botPassword: String = environment.config.tryGetString("bot.auth.password") ?: ""
    val mmBotToken: String = environment.config.tryGetString("bot.auth.mmtoken") ?: ""
    runBlocking {
       // println(JiraClient(botUsername, botPassword).getIssue("MMJIR-5"))
        println(MmClient(mmBotToken).getUsers())
    }
}
