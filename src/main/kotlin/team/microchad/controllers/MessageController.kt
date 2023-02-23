package team.microchad.controllers

import team.microchad.client.JiraClient
import team.microchad.client.MmClient
import team.microchad.dto.mm.IncomingMsg
import team.microchad.dto.mm.OutgoingMsg
import team.microchad.service.MarkdownMessage

class MessageController {

    companion object {
        const val BOT_NAME = "Best Bot"

        const val HELP_COMMAND = "!help"
        const val ISSUES_COMMAND = "!issues"
        const val JQL_COMMAND = "!jql"

        const val HELP_MSG = "Sorry, I can't help you"
        const val PARAM_COUNT_EXCEPTION = "Exception: parameter exception"
    }

    suspend fun chooseCommand(incomingMsg: IncomingMsg) {
        val message = incomingMsg.text.split(" ")
        when (message[1]) {
            HELP_COMMAND -> sendBack(incomingMsg, HELP_MSG)
            JQL_COMMAND -> return
            ISSUES_COMMAND -> {
                if (message.size >= 4) {
                    var jiraJqlResponse = JiraClient().getIssues(message[2], message[3])
                   // var outgoingMsg1 = MarkdownMessage(jiraJqlResponse, incomingMsg).getFormattedMessage()
                 //   sendBack(incomingMsg, outgoingMsg1)
                }
                else sendBack(incomingMsg, PARAM_COUNT_EXCEPTION)
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