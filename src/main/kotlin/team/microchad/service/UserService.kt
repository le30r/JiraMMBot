package team.microchad.service

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

import team.microchad.model.repositories.UserRepository

class UserService : KoinComponent {
    private val repository: UserRepository by inject()

    //TODO: Implement user registration into database
    fun registerUser(userId: String, jiraUserId: String) {
        println("User $userId is registered with $jiraUserId")
    }
}