package team.microchad.service

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import team.microchad.model.entities.UserMap

import team.microchad.model.repositories.UserMapRepository

class UserService : KoinComponent {
    private val repository: UserMapRepository by inject()

    suspend fun registerUser(userId: String, jiraUserId: String) {
        repository.create(UserMap(mmUsername = userId, jiraUsername = jiraUserId))
    }

    suspend fun getJiraUsername(mmUsername: String): String {
        return repository.findById(mmUsername)?.jiraUsername.orEmpty()
    }
}