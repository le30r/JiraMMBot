package team.microchad.controllers

import team.microchad.client.JiraClient
import team.microchad.client.MikeBot
import team.microchad.dto.mm.IncomingMsg
import team.microchad.dto.mm.OutgoingMsg

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
        when (message[0]) {
            HELP_COMMAND -> sendBack(incomingMsg, HELP_MSG)
            JQL_COMMAND -> return
            ISSUES_COMMAND -> {
                if (message.size > 3) JiraClient().getIssues(message[1], message[2])
                else sendBack(incomingMsg, PARAM_COUNT_EXCEPTION)
            }
        }
    }

    private suspend fun sendBack(incomingMsg: IncomingMsg, text: String) {
        MikeBot().send(
            OutgoingMsg(
                text = text,
                channel = incomingMsg.channel_name,
                username = BOT_NAME
            )
        )
    }

}