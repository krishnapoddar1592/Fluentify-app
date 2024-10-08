package com.example.fluentifyapp.data.api



import com.example.fluentifyapp.data.model.CourseSummaryDTO
import com.example.fluentifyapp.data.model.FillQuestionResponse
import com.example.fluentifyapp.data.model.HomeInfo
import com.example.fluentifyapp.data.model.LessonProgressDTO
import com.example.fluentifyapp.data.model.MatchQuestionResponse
import com.example.fluentifyapp.data.model.User
import com.example.fluentifyapp.data.model.UserRequest
import com.example.fluentifyapp.data.model.UserResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

// data/api/UserService.kt
interface UserService {

    @GET("users/{id}")
    suspend fun getUser(@Path("id") userId: String): UserResponse

    @POST("users")
    suspend fun createUser(@Body userRequest: UserRequest): Response<Unit>

    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") userId: String, @Body userRequest: UserRequest): UserResponse

    @GET("users/{id}/home-info")
    suspend fun getUserHomeInfo(@Path("id") userId: String): HomeInfo

    @GET("users/{id}/exploreCourses")
    suspend fun getNewCoursesForUser(@Path("id") userId: String):Response<List<CourseSummaryDTO>>

    @GET("users/{id}/courses/{courseId}/lessonInfo/{lessonId}")
    suspend fun getLessonStartPageData(@Path("id") userId: String, @Path("courseId") courseId: Int, @Path("lessonId") lessonId: Int):Response<LessonProgressDTO>

    @POST("users/{id}/courses/{courseId}")
    suspend fun enrollUserToCourse(@Path("id") userId: String, @Path("courseId") courseId: Int):retrofit2.Response<Unit>

    @POST("users/{id}/courses/{courseId}/lessons/{lessonId}/questions/{questionId}")
    suspend fun answerMatchQuestion(@Path("id") userId: String, @Path("courseId") courseId: Int, @Path("lessonId") lessonId: Int, @Path("questionId") questionId: Int, @Body answer: MatchQuestionResponse): Response<Unit>

    @POST("users/{id}/courses/{courseId}/lessons/{lessonId}/questions/{questionId}")
    suspend fun answerFillQuestion(@Path("id") userId: String, @Path("courseId") courseId: Int, @Path("lessonId") lessonId: Int, @Path("questionId") questionId: Int, @Body answer: FillQuestionResponse): Response<Unit>

}



