package team.microchad.config

class MattermostConfiguration {
    //TODO get this values from application.yaml or PATH variables
    val webhookUrl = "tin-workshop.ddns.net:8065/hooks" //TODO same shit as in JiraConfiguration.kt
    val token = "ns9hipwfn7d17dgaxt7ao4ouor"
    val api = "tin-workshop.ddns.net:8065/api/v4/channels/direct"
    val botId = "513n8jxeciramrye4k7ka863qa"
    val posts = "tin-workshop.ddns.net:8065/api/v4/posts"
    val openDialog = "tin-workshop.ddns.net:8065/api/v4/actions/dialogs/open"
}
