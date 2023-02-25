package team.microchad.plugins

import io.ktor.server.application.*
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.singleOf

import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import team.microchad.client.MmClient
import team.microchad.config.JiraConfiguration
import team.microchad.config.MattermostConfiguration

fun Application.configureDI() {
    install(Koin) {
        slf4jLogger()
        modules(mmBotModule)

    }

}

val mmBotModule = module {
    factory {
        MmClient()
    }

    singleOf(::MattermostConfiguration) {
        createdAtStart()
    }

    singleOf(::JiraConfiguration) {
        createdAtStart()
    }
}