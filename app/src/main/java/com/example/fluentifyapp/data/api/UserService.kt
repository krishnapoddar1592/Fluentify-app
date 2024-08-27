package com.example.fluentifyapp.data.api



import com.example.fluentifyapp.data.model.HomeInfo
import com.example.fluentifyapp.data.model.User
import com.example.fluentifyapp.data.model.UserRequest
import com.example.fluentifyapp.data.model.UserResponse
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
    suspend fun createUser(@Body userRequest: UserRequest): retrofit2.Response<Unit>

    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") userId: String, @Body userRequest: UserRequest): UserResponse

    @GET("users/{id}/home-info")
    suspend fun getUserHomeInfo(@Path("id") userId: String): HomeInfo
}



