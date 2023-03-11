package team.microchad.plugins

import io.ktor.server.application.*
import io.ktor.server.config.*

object Secrets {
    lateinit var botUsername: String
    lateinit var botPassword: String
    lateinit var botToken: String
    lateinit var botHost: String
    //TODO: May be move db setting into another object?
    lateinit var dbHost: String
    lateinit var dbPort: String
    lateinit var dbName: String
    lateinit var dbLogin: String
    lateinit var dbPassword: String
}

fun Application.getSecrets() {
    Secrets.botUsername = environment.config.tryGetString("bot.auth.username") ?: ""
    Secrets.botPassword = environment.config.tryGetString("bot.auth.password") ?: ""
    Secrets.botToken = environment.config.tryGetString("bot.auth.token") ?: ""
    Secrets.botHost = environment.config.tryGetString("bot.host") ?: ""
    Secrets.dbHost = environment.config.tryGetString("db.url") ?: ""
    Secrets.dbPort = environment.config.tryGetString("db.port") ?: ""
    Secrets.dbName = environment.config.tryGetString("db.name") ?: ""
    Secrets.dbLogin = environment.config.tryGetString("db.user") ?: ""
    Secrets.dbPassword = environment.config.tryGetString("db.password") ?: ""
}
