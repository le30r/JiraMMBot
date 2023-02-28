package team.microchad.plugins

import io.ktor.server.application.*
import io.ktor.server.config.*

object Secrets {
    lateinit var botUsername: String
    lateinit var botPassword: String
    lateinit var botToken: String
}

fun Application.getSecrets() {
    Secrets.botUsername = environment.config.tryGetString("bot.auth.username") ?: ""
    Secrets.botPassword = environment.config.tryGetString("bot.auth.password") ?: ""
    Secrets.botToken = environment.config.tryGetString("bot.auth.token") ?: ""
}
