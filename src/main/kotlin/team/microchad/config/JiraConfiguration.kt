package team.microchad.config

import team.microchad.plugins.Secrets

class JiraConfiguration {
    //TODO Get this values from application.yaml or PATH variables
    val baseUrl = "tin-workshop.ddns.net:8080"
    val apiPath = "rest/api/2/search/jql"
    val botUsername = Secrets.botUsername
    val botPassword = Secrets.botPassword
}