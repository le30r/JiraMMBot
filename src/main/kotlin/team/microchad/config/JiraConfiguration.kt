package team.microchad.config

import team.microchad.plugins.Secrets

class JiraConfiguration {
    //TODO Get this values from application.yaml or PATH variables
    val baseUrl =
        "tin-workshop.ddns.net:8080"  //TODO Use localhost:$PORT instead hardcoded URL, maybe separate test and prod parameters
    val apiPath = "rest/api/2"
    val searchJql = "search/jql"
    val issue = "issue"
    val comment = "comment"
    val botUsername = Secrets.botUsername
    val botPassword = Secrets.botPassword
}
