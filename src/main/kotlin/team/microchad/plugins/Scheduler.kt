package team.microchad.plugins

import io.ktor.client.statement.*
import io.ktor.server.application.*
import it.justwrote.kjob.InMem
import it.justwrote.kjob.KronJob
import it.justwrote.kjob.job.JobExecutionType
import it.justwrote.kjob.kjob
import it.justwrote.kjob.kron.Kron
import it.justwrote.kjob.kron.KronModule
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import team.microchad.client.JiraClient
import team.microchad.client.MmClient
import team.microchad.dto.mm.OutgoingMsg
import team.microchad.service.markdown

val scheduler = kjob(InMem) {
    extension(KronModule)
}.start()


fun Application.setupScheduler() {

    scheduler(Kron).kron(SendMessage) {
        executionType = JobExecutionType.BLOCKING
        maxRetries = 3
        execute {
            it.execute()
        }.onError {

        }
    }
}


object SendMessage : KronJob("send-message", "*/5 * * ? * * *"), KoinComponent {
    private val jiraClient: JiraClient by inject()
    private val mmClient: MmClient by inject()
    suspend fun execute() {
        val result = mmClient.sendToDirectChannel(
            OutgoingMsg(
                "mdogqunhyt8j5cwes4d9g6em4r",
                markdown {
                    h1 {
                        "I'm scheduled message"
                    }
                }
            )
        )
        println(result.bodyAsText())
    }
}
