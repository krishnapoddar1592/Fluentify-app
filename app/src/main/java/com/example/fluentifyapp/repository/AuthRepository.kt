package com.example.fluentifyapp.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface AuthRepository {
    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<FirebaseUser>
    suspend fun createUserWithEmailAndPassword(email: String, password: String): Result<FirebaseUser>
}

class AuthRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) : AuthRepository {
    override suspend fun signInWithEmailAndPassword(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(result.user!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createUserWithEmailAndPassword(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Result.success(result.user!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun checkIfEmailExists(email: String): Boolean {
        return try {
            val signInMethods = firebaseAuth.fetchSignInMethodsForEmail(email).await()
            println("Sign-in methods: ${signInMethods.signInMethods}")
            if (signInMethods.signInMethods?.isNotEmpty() == true) {
                println("Email exists")
                true // Email is already registered
            } else {
                println("Email does not exist")
                false // Email is not registered
            }
        } catch (e: Exception) {
            println("Exception occurred: ${e.message}")
            throw e
        }
    }

}