package team.microchad.service

import team.microchad.dto.jira.Issue
import team.microchad.dto.mm.OutgoingMsg

fun getOutgoingMessageForIssues(channelId: String, issues: Array<Issue>?): OutgoingMsg {
    return if (!issues.isNullOrEmpty()) OutgoingMsg(channelId, getMarkdownTableForIssues(issues))
    else OutgoingMsg(channelId, getMarkdownError())
}

fun getMarkdownError(): String =
    markdown {
        h3 {
            "No issues found in Jira. Enjoy your day :grin: or check your input :no_mouth:"
        }
    }


fun getMarkdownTableForIssues(issues: Array<Issue>): String =
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
            for (issue in issues) {
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