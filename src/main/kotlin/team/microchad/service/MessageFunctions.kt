package team.microchad.service

import org.koin.java.KoinJavaComponent.inject
import team.microchad.config.JiraConfiguration
import team.microchad.dto.jira.Issue
import team.microchad.dto.jira.JiraJqlResponse
import team.microchad.dto.mm.OutgoingMsg

fun getOutgoingMessageForIssues(channelId: String, issues: Array<Issue>?, status: String): OutgoingMsg {
    return if (!issues.isNullOrEmpty()) OutgoingMsg(channelId, getMarkdownTableForIssues(issues, status))
    else OutgoingMsg(channelId, getMarkdownError(status))
}

fun getMarkdownError(status: String): String =
    markdown {
        h3 {
            "No issues with status $status found in Jira. Enjoy your day :grin: or check your input :no_mouth:"
        }
    }


fun getMarkdownTableForIssues(issues: Array<Issue>, status: String): String =
    markdown {
        h1{
            "Issues with status $status:"
        }
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
                                    "[Go to...](http://${JiraConfiguration().baseUrl}/browse/$key)"
                                }
                            }
                        }
                    }
                }
            }
        }
    }