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
import team.microchad.model.repositories.ProjectMapRepository
import team.microchad.plugins.Secrets
import team.microchad.service.getIssuesByStatusExprAndProject
import team.microchad.service.getOutgoingMessageForIssues

val scheduler = kjob(JdbiKJob) {
    connectionString = with(Secrets) {
        "jdbc:postgresql://$dbHost:$dbPort/kjob?user=$dbLogin&password=$dbPassword" // JDBC connection string
    }

    extension(KronModule)
}.start()

fun scheduleMessageSending(name: String ,cronExpression: String, statusExpression: String) {
    scheduler(Kron).kron(MessageJob(name, cronExpression, statusExpression)) {
        executionType = JobExecutionType.BLOCKING

        maxRetries = 3
        execute {
            it.sendMessageWithResultOfJql()
        }
    }
}

class MessageJob(name: String, cron: String, private val statusExpression: String) : KronJob(name, cron), KoinComponent {
    private val jiraClient: JiraClient by inject()
    private val mmClient: MmClient by inject()
    private val repository: ProjectMapRepository by inject()
    //TODO: implement channel selection
    suspend fun sendMessageWithResultOfJql() {

        for (project in repository.findAll()) {
            val jql = getIssuesByStatusExprAndProject(statusExpression, project.project)
            val result = jiraClient.getByJql(jql)

            val msg = getOutgoingMessageForIssues(project.chat, result.issues)
            val serverResponse = mmClient.sendToDirectChannel(msg)
            println(serverResponse)
        }


    }
}

