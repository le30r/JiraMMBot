package team.microchad.controllers

import team.microchad.client.JiraClient
import team.microchad.client.MmClient
import team.microchad.dto.mm.IncomingMsg
import team.microchad.dto.mm.OutgoingMsg
import team.microchad.exceptions.JiraBadRequestException
import team.microchad.service.MarkdownMessage
import team.microchad.service.markdown


class MessageController {

    companion object {
        const val BOT_NAME = "Best Bot"

        const val HELP_COMMAND = "!help"
        const val ISSUES_COMMAND = "!issues"
        const val JQL_COMMAND = "!jql"
        const val COMMENT_COMMAND = "!comment"

        const val HELP_MSG = "Sorry, I can't help you"
        const val PARAM_COUNT_EXCEPTION = "Exception: parameter exception"
        const val BAD_REQUEST = "Exception: bad request"
        const val SUCCESSFUL = "Command executed successful"

        const val COMMAND = 1
        const val PARAMETER_1 = 2
        const val PARAMETER_2 = 3

    }

    suspend fun chooseCommand(incomingMsg: IncomingMsg) {
        val message = incomingMsg.text.split(" ")
        when (message[COMMAND]) {
            HELP_COMMAND -> sendBack(incomingMsg, HELP_MSG)
            JQL_COMMAND -> {
                try {
                    var jiraResponse = JiraClient().getByJql(message[PARAMETER_1])
                    var outgoingMsg = ""//TODO Formatted output

                    sendBack(incomingMsg, outgoingMsg)
                } catch (exception: JiraBadRequestException) {
                    sendBack(incomingMsg, BAD_REQUEST)
                }
            }

            COMMENT_COMMAND -> {
                if (JiraClient().commentIssue(message[PARAMETER_1], message[PARAMETER_2]))
                    sendBack(incomingMsg, SUCCESSFUL)
                else sendBack(incomingMsg, BAD_REQUEST)
            }

            ISSUES_COMMAND -> {
                if (message.size >= 4) {
                    var jiraJqlResponse = JiraClient().getByJql(message[PARAMETER_1])//TODO Formatted output
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