package team.microchad.plugins

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun Application.configDatabase() {
    Database.connect("jdbc:postgresql://localhost:5432/mmbot", "org.postgresql.Driver", "postgres", "0000")
}