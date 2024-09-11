package com.example.fluentifyapp.data.repository

import com.example.fluentifyapp.data.model.CourseSummaryDTO
import com.example.fluentifyapp.data.model.FillQuestionResponse
import com.example.fluentifyapp.data.model.HomeInfo
import com.example.fluentifyapp.data.model.LessonProgressDTO
import com.example.fluentifyapp.data.model.MatchQuestionResponse
import com.example.fluentifyapp.data.model.User
import com.example.fluentifyapp.data.model.UserRequest

// domain/repository/UserRepository.kt
interface UserRepository {
    suspend fun getUser(userId: String): User
    suspend fun createUser(userRequest: UserRequest): Result<Unit>
    suspend fun updateUser(userId: String, userRequest: UserRequest): User
    suspend fun enrollUserToCourse(userId: String, courseId: Int): Result<Unit>
    suspend fun getHomeInfo(userId: String): HomeInfo
    suspend fun getNewCoursesForUser(userId: String): Result<List<CourseSummaryDTO>>
    suspend fun getLessonStartInfo(userId: String, courseId: Int, lessonId: Int): Result<LessonProgressDTO>
    suspend fun answerMatchQuestion(userId: String, courseId: Int, lessonId: Int, questionId: Int, answer: MatchQuestionResponse): Result<Unit>
    suspend fun answerFillQuestion(userId: String, courseId: Int, lessonId: Int, questionId: Int, answer: FillQuestionResponse): Result<Unit>
}
