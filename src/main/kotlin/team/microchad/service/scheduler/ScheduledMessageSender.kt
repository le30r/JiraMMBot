package team.microchad.service.scheduler

import it.justwrote.kjob.InMem
import it.justwrote.kjob.KronJob
import it.justwrote.kjob.kjob
import it.justwrote.kjob.kron.Kron
import it.justwrote.kjob.kron.KronModule
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import team.microchad.client.JiraClient
import team.microchad.client.MmClient


val scheduler = kjob(InMem) {
    extension(KronModule)
}.start()

class ScheduledMessageSender (cronExpression: String) : KronJob("send-message", cronExpression), KoinComponent {
    private val jiraClient: JiraClient by inject()
    private val mmClient: MmClient by inject()
}
fun scheduleMessageSending(cronExpression: String, executeFun: ()->Unit) {
    scheduler(Kron).kron(ScheduledMessageSender(cronExpression)) {
        execute {
            executeFun()
        }
    }
}