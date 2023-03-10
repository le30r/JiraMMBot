package team.microchad.service.scheduler

import kjob.core.KronJob
import kjob.core.job.JobExecutionType
import kjob.core.kjob
import kjob.jdbi.JdbiKJob
import kjob.kron.Kron
import kjob.kron.KronModule
import kotlinx.coroutines.coroutineScope
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import team.microchad.client.JiraClient
import team.microchad.client.MmClient
import team.microchad.service.getOutgoingMessageForIssues

val scheduler = kjob(JdbiKJob) {
    connectionString =
        "jdbc:postgresql://localhost:5432/kjob?user=postgres&password=postgres" // JDBC connection string
    extension(KronModule)
}.start()

fun scheduleMessageSending(name: String ,cronExpression: String, jql: String) {
    scheduler(Kron).kron(MessageJob(name, cronExpression, jql)) {
        executionType = JobExecutionType.BLOCKING

        maxRetries = 3
        execute {
            it.sendMessageWithResultOfJql()
        }
    }
}

class MessageJob(name: String, cron: String, private val jql: String) : KronJob(name, cron), KoinComponent {
    private val jiraClient: JiraClient by inject()
    private val mmClient: MmClient by inject()
    //TODO: implement channel selection
    suspend fun sendMessageWithResultOfJql() {
        val result = jiraClient.getByJql(jql)
        val msg = getOutgoingMessageForIssues("", result.issues)
        mmClient.send(msg)
    }
}

