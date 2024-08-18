package com.example.fluentifyapp.data.model

// data/model/user/User.kt
data class User(
    val userId: String,
    val email: String,
    val name: String,
    val dob: String,  // Date handling in Android can use String or Date formats; LocalDate needs conversion if using it directly
    val language: String,
    val courses: List<Course>?,
    val achievements: List<UserAchievement>?,
    val currentCourse: Course?,
    val courseProgress: List<CourseProgress>?
)

// data/model/user/UserRequest.kt
data class UserRequest(
    val userId: String,
    val email: String,
    val name: String,
    val dob: String,  // Date handling in Android can use String or Date formats; LocalDate needs conversion if using it directly
    val language: String,

)

// data/model/user/UserResponse.kt
data class UserResponse(
    val name: String,
    val email: String,
    val dob: String,
    val language: String,
    val currentCourse:Course,
    val userId: String
)

