package com.example.fluentifyapp.data.repository

import com.example.fluentifyapp.data.model.User
import com.example.fluentifyapp.data.model.UserRequest

// domain/repository/UserRepository.kt
interface UserRepository {
    suspend fun getUser(userId: String): User
    suspend fun createUser(userRequest: UserRequest): User
    suspend fun updateUser(userId: String, userRequest: UserRequest): User
}