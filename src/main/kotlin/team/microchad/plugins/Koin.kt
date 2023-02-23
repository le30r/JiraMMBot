package team.microchad.plugins

import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import team.microchad.client.MmClient

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

}