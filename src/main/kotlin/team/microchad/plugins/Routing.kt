package team.microchad.plugins

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

import org.koin.ktor.ext.inject

import team.microchad.client.JiraClient
import team.microchad.client.MmClient
import team.microchad.dto.mm.OutgoingMsg
import team.microchad.dto.mm.dialog.Response
import team.microchad.dto.mm.dialog.submissions.SelectionSubmission
import team.microchad.dto.mm.createMessageFromParam
import team.microchad.dto.mm.dialog.submissions.StatisticsSubmission
import team.microchad.service.*


fun Application.configureRouting() {
    val mmClient: MmClient by inject()
    val jiraClient: JiraClient by inject()
    val userService: UserService by inject()
    routing {

        get("/") {
            call.respondText("Hello, world!")
        }

        post("/") {
            //TODO: Change endpoint after testing, move code into another class or fun
            val statuses = jiraClient.getStatuses()
            val projects = jiraClient.getProjects()
            val incomingMsg = createMessageFromParam(call.receiveParameters())
            val dialog = createStatisticsDialog(incomingMsg.triggerId, statuses, projects)
            mmClient.openDialog(dialog)
            call.respondText(
                markdown {
                    bold {
                        "Continue in dialog window"
                    }
                }
            )
        }

        post("/statistics") {
            //TODO: Extract to method
            val incoming = call.receive<Response<StatisticsSubmission>>()
            val status = incoming.submission?.selectStatus
            val project = incoming.submission?.selectProject
            println("${status}, $project")
            val responseJira = jiraClient.getByJql(getIssuesWithChangedStatus(status ?: ""))
            val channelId = mmClient.createDirectChannel(incoming.userId)
            mmClient.sendToDirectChannel(
               OutgoingMsg(channelId,
               markdown {
                   table {
                       headerRow {
                           column {
                               "Key"
                           }
                           column {
                               "Summary"
                           }
                           column {
                               "Project"
                           }
                           column {
                               "Link"
                           }
                       }
                       for (issue in responseJira.issues) {
                           with(issue) {
                               row {
                                   column {
                                       key
                                   }
                                   column {
                                       fields.summary
                                   }
                                   column {
                                       fields.project.key
                                   }
                                   column {
                                       markdown {
                                           bold {
                                               //TODO: make link to issue (right now it redirects to Jira REST API
                                               "[Go to...]($self)"
                                           }
                                       }
                                   }
                               }
                           }
                       }
                   }
               }
            ))
        }

        post("/dialog") {
            println(call.receiveText())
        }

        post("/register_dialog") {
            val incomingMsg = createMessageFromParam(call.receiveParameters())
            val users = jiraClient.getUsers()
            val dialog = createRegisterJiraDialog(incomingMsg.triggerId, users)
            mmClient.openDialog(dialog)
            call.respondText(
                markdown {
                    bold {
                        "Continue registration in dialog window"
                    }
                }
            )
        }

        post("/register_user") {
            //todo: implement adding into DB
            //println(call.receiveText())
            val result = call.receive<Response<SelectionSubmission>>()
            if (!result.cancelled) {
                with(result) {
                    userService.registerUser(userId, submission!!.jiraUser)
                }
            }
        }

    }
}
