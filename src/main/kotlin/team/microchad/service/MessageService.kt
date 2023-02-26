package team.microchad.service

import team.microchad.client.JiraClient
import team.microchad.client.MmClient
import team.microchad.dto.mm.IncomingMsg
import team.microchad.dto.mm.OutgoingMsg
import team.microchad.exceptions.JiraBadRequestException


class MessageService {

    companion object {
        const val BOT_NAME = "Best Bot"

        const val COMMAND_HELP = "!help"
        const val COMMAND_ISSUES = "!issues"
        const val COMMAND_JQL = "!jql"
        const val COMMAND_COMMENT = "!comment"

        const val HELP_MSG = "There are few commands that implemented for this moment:\n" +
                "1. stats !issues {name of assignee}\n" +
                "2. stats !jql {JQL query}\n" +
                "3. stats !comment {Issue key} {Your comment}\n"
        const val PARAM_COUNT_EXCEPTION = "Exception: parameter exception"
        const val BAD_REQUEST = "Exception: bad request"
        const val SUCCESSFUL = "Command executed successful"

        const val POS_COMMAND = 1
        const val POS_PARAMETER_1 = 2
        const val POS_PARAMETER_2 = 3

    }

    suspend fun chooseCommand(incomingMsg: IncomingMsg) {
        val message = incomingMsg.text.split(" ")
        when (message[POS_COMMAND]) {
            COMMAND_HELP -> sendBack(incomingMsg, HELP_MSG)
            COMMAND_JQL -> {
                try {
                    var jiraResponse = JiraClient().getByJql(message[POS_PARAMETER_1])
                    var outgoingMsg = ""//TODO Formatted output

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
                if (message.size >= 4) {
                    var jiraJqlResponse = JiraClient().getByJql(message[POS_PARAMETER_1])//TODO Formatted output
                    // var outgoingMsg1 = MarkdownMessage(jiraJqlResponse, incomingMsg).getFormattedMessage()
                    //   sendBack(incomingMsg, outgoingMsg1)
                } else sendBack(incomingMsg, PARAM_COUNT_EXCEPTION)
            }
        }
    }

    private suspend fun sendBack(incomingMsg: IncomingMsg, text: String) {

        MmClient().send(
            OutgoingMsg(
                text = text,
                channel = incomingMsg.channel_name,
                username = BOT_NAME
            )
        )
    }

}