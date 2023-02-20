package team.microchad.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import team.microchad.client.JiraClient
import team.microchad.client.MikeBot
import team.microchad.model.dao.CrudRepository
import team.microchad.model.dao.ProjectRepository
import team.microchad.model.entities.Project
import team.microchad.model.entities.Projects

fun Application.configureDI() {
    install(Koin) {
        slf4jLogger()
        modules(mmBotModule)
    }

}

val mmBotModule = module {
    factory {
        MikeBot()
    }
   singleOf(::ProjectRepository) {
       bind<CrudRepository<Project>>()
       createdAtStart()
   }

}