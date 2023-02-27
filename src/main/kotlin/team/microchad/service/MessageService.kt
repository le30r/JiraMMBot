package team.microchad.service

import team.microchad.client.JiraClient
import team.microchad.client.MmClient
import team.microchad.dto.mm.IncomingMsg
import team.microchad.dto.mm.OutgoingMsg
import team.microchad.exceptions.JiraBadRequestException
import team.microchad.utils.deleteDoubleSpace


class MessageService {

    companion object {
        const val BOT_NAME = "Best Bot"

        const val COMMAND_HELP = "!help"
        const val COMMAND_ISSUES = "!issues"
        const val COMMAND_JQL = "!jql"
        const val COMMAND_COMMENT = "!comment"

        const val HELP_MSG = "##### There are few commands that implemented for this moment:\n" +
                "**1. stats !issues {name of assignee}**\n" +
                "**2. stats !comment {Issue key} {Your comment}**\n"
        const val PARAM_COUNT_EXCEPTION = "Exception: parameter exception"
        const val BAD_REQUEST = "**Exception: bad request. Use _stats !help_ to check correctness**"
        const val SUCCESSFUL = "Command executed successful"

        const val POS_COMMAND = 1
        const val POS_PARAMETER_1 = 2
        const val POS_PARAMETER_2 = 3
    }

    suspend fun chooseCommand(incomingMsg: IncomingMsg) {
        val message = incomingMsg.text.deleteDoubleSpace().split(" ")
        when (message[POS_COMMAND]) {
            COMMAND_HELP -> sendBack(incomingMsg, HELP_MSG)
            COMMAND_JQL -> {
                try {
                    val jiraResponse = JiraClient().getByJql(message[POS_PARAMETER_1])
                    val outgoingMsg = jiraResponse.toString()//TODO Formatted output
                    sendBack(incomingMsg, outgoingMsg)
                } catch (exception: JiraBadRequestException) {
                    sendBack(incomingMsg, BAD_REQUEST)
                }
            }
            COMMAND_COMMENT -> {
                if (JiraClient().commentIssue(message[POS_PARAMETER_1], message[POS_PARAMETER_2]))
                    sendBack(incomingMsg, SUCCESSFUL)
                else sendBack(incomingMsg, BAD_REQUEST)
            }
            COMMAND_ISSUES -> {
                if (message.size >= 3) {
                    val jiraJqlResponse = JiraClient().getUserIssuesSortedByStatus(message[2])//TODO Formatted output
                    val outgoingMsg = markdown {
                        h1 {
                            "Issues for user ${message[2]}"
                        }
                        table {
                            headerRow {
                                column { "Summary" }
                                column { "Updated" }
                            }
                            for (issue in jiraJqlResponse.issues) {
                                row {
                                    column { issue.fields.summary }
                                    column { issue.fields.updated.orEmpty() }
                                }
                            }
                        }
                    }
                    sendBack(incomingMsg, outgoingMsg)
                } else sendBack(incomingMsg, PARAM_COUNT_EXCEPTION)
            }
            else -> {
                sendBack(incomingMsg, BAD_REQUEST)
            }
        }
    }

    private suspend fun sendBack(incomingMsg: IncomingMsg, text: String) {

//        MmClient().send(
//            OutgoingMsg(
//                text = text,
//                channel = incomingMsg.channel_name,
//                username = BOT_NAME
//            )
//        )
    }

}