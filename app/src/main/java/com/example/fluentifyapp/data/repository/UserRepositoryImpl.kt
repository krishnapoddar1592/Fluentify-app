package com.example.fluentifyapp.data.repository

import android.util.Log
import com.example.fluentifyapp.data.api.UserService
import com.example.fluentifyapp.data.model.CourseSummaryDTO
import com.example.fluentifyapp.data.model.FillQuestionResponse
import com.example.fluentifyapp.data.model.HomeInfo
import com.example.fluentifyapp.data.model.LessonProgressDTO
import com.example.fluentifyapp.data.model.MatchQuestionResponse
import com.example.fluentifyapp.data.model.User
import com.example.fluentifyapp.data.model.UserRequest
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject


// data/repository/UserRepositoryImpl.kt
class UserRepositoryImpl @Inject constructor(
    private val userService: UserService
) : UserRepository {

    override suspend fun getUser(userId: String): User {
        val response = userService.getUser(userId)
        return User(response.userId, response.name, response.email, response.dob,response.language,null,null,response.currentCourse,null)
    }

    override suspend fun createUser(userRequest: UserRequest): Result<Unit> {
        return try {
            val response = userService.createUser(userRequest)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to create user: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateUser(userId: String, userRequest: UserRequest): User {
        val response = userService.updateUser(userId, userRequest)
        return User(response.userId, response.name, response.email, response.dob,response.language,null,null,response.currentCourse,null)
    }

    override suspend fun enrollUserToCourse(userId: String, courseId: Int): Result<Unit> {
        val response =userService.enrollUserToCourse(userId,courseId)
        return if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            Result.failure(Exception("Failed to add course: ${response.code()}"))
        }
    }

    override suspend fun getHomeInfo(userId: String): HomeInfo {
        val response=userService.getUserHomeInfo(userId)
        return response
    }

    override suspend fun getNewCoursesForUser(userId: String): Result<List<CourseSummaryDTO>> {
        return try {
            val response = userService.getNewCoursesForUser(userId)
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getLessonStartInfo(
        userId: String,
        courseId: Int,
        lessonId: Int
    ): Result<LessonProgressDTO> {
        return try{
            val response=userService.getLessonStartPageData(userId,courseId,lessonId)
            if(response.isSuccessful){
                Result.success(response.body() ?: LessonProgressDTO(0,0,"","","","",0,0))
            }else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)

        }
    }

    override suspend fun answerMatchQuestion(
        userId: String,
        courseId: Int,
        lessonId: Int,
        questionId: Int,
        answer: MatchQuestionResponse
    ): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("ApiCall", "Submitting match question: userId=$userId, questionId=$questionId")
                val response = userService.answerMatchQuestion(userId, courseId, lessonId, questionId, answer)
                Log.d("ApiCall", "Match question response: ${response.isSuccessful}")

                if (response.isSuccessful) {
                    Result.success(Unit)
                } else {
                    Result.failure(HttpException(response))
                }
            } catch (e: Exception) {
                Log.e("ApiCall", "Error submitting match question: ${e.message}")
                Result.failure(e)
            }
        }
    }

    override suspend fun answerFillQuestion(
        userId: String,
        courseId: Int,
        lessonId: Int,
        questionId: Int,
        answer: FillQuestionResponse
    ): Result<Unit> {
        return withContext(Dispatchers.IO){ 
            try {
                Log.e("ApiCall","$userId $courseId $lessonId $questionId $answer")
                val response=userService.answerFillQuestion(userId,courseId,lessonId,questionId,answer)
                Log.e("ApiCall","$response")
                if(response.isSuccessful){
                    Result.success(Unit)
                }else{
                    Result.failure(HttpException(response))
                }
            }catch (e:Exception){
                Result.failure(e)
            }
        }
    }

}
