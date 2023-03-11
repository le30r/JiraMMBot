package team.microchad.plugins

import io.ktor.server.application.*

import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

import team.microchad.client.JiraClient
import team.microchad.client.MmClient
import team.microchad.config.JiraConfiguration
import team.microchad.config.MattermostConfiguration
import team.microchad.model.repositories.UserMapRepository
import team.microchad.model.repositories.ProjectMapRepository
import team.microchad.service.UserService

fun Application.configureDI() {
    install(Koin) {
        slf4jLogger()
        modules(mmBotModule)

    }

}

val mmBotModule = module {
    singleOf(::MattermostConfiguration) {
        createdAtStart()
    }

    singleOf(::JiraConfiguration) {
        createdAtStart()
    }

    singleOf(::UserMapRepository) {
        createdAtStart()
    }

    singleOf(::ProjectMapRepository) {
        createdAtStart()
    }
    factoryOf(::UserService)
    factoryOf(::MmClient)
    factoryOf(::JiraClient)
}