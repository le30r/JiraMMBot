package team.microchad.service

import team.microchad.dto.jira.User
import team.microchad.dto.mm.dialog.Dialog
import team.microchad.dto.mm.dialog.elements.Option
import team.microchad.dto.mm.dialog.elements.SelectElement
import java.util.UUID

class DialogConstructor {
    fun setSelectUser(users: Array<User>): Dialog {
        val options = ArrayList<Option>()
        for (user in users) {
            options.add(Option("jira_user", user.name))
        }
        val selectElement: SelectElement = SelectElement("jira user", "jira_user_select", options = options)
        selectElement.placeholder = "Choose Jira user"
        return Dialog(UUID.randomUUID().toString(), "Select Jira User", null, listOf(selectElement))
    }
}