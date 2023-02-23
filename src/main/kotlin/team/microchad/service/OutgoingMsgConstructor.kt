package team.microchad.service

import team.microchad.dto.jira.Issue
import team.microchad.dto.jira.JiraJqlResponse
import team.microchad.dto.mm.IncomingMsg

class OutgoingMsgConstructor(var response: JiraJqlResponse, var incomingMsg: IncomingMsg) {
    private var outgoingMessage: String = ""
    private val status = MsgService(incomingMsg).getStatus()

    fun getFormattedMessage(): String {
        setHeader()
        setTableHeader()
        setTableRows(response.issues)
        return outgoingMessage
    }

    private fun setHeader() {
        outgoingMessage = outgoingMessage.plus(
            "${
                h3(
                    "Issues for user ${italics(incomingMsg.user_name)} " +
                            "with status ${italics(status)} are presented in the following table:"
                )
            }\n"
        )
    }

    private fun setTableHeader() {
        outgoingMessage += "|Summary|Updated|\n|:----:|:----:|\n"
    }

    private fun setTableRows(issues: Array<Issue>) {
        for (issue in issues) {
            outgoingMessage +="|${issue.fields.summary}|${issue.fields.updated}|\n"
        }
    }

    private fun italics(msg: String): String = "_${msg}_"

    private fun bold(msg: String): String = "**${msg}**"

    private fun strikethrough(msg: String): String = "~~${msg}~~"

    private fun inLineCode(msg: String): String = "'${msg}'"

    private fun hyperlink(msg: String): String = "[hyperlink](${msg})"

    private fun h1(msg: String): String = "## $msg"

    private fun h2(msg: String): String = "### $msg"

    private fun h3(msg: String): String = "#### $msg"

}