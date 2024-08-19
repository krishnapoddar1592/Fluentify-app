package com.example.fluentifyapp.data.repository

import com.example.fluentifyapp.data.api.UserService
import com.example.fluentifyapp.data.model.User
import com.example.fluentifyapp.data.model.UserRequest
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject


// data/repository/UserRepositoryImpl.kt
class UserRepositoryImpl @Inject constructor(
    private val userService: UserService
) : UserRepository {

    override suspend fun getUser(userId: String): User {
        val response = userService.getUser(userId)
        return User(response.userId, response.name, response.email, response.dob,response.language,null,null,response.currentCourse,null)
    }

    override suspend fun createUser(userRequest: UserRequest): User {
        val response = userService.createUser(userRequest)
        return User(response.userId, response.name, response.email, response.dob,response.language,null,null,response.currentCourse,null)
    }

    override suspend fun updateUser(userId: String, userRequest: UserRequest): User {
        val response = userService.updateUser(userId, userRequest)
        return User(response.userId, response.name, response.email, response.dob,response.language,null,null,response.currentCourse,null)
    }
}
