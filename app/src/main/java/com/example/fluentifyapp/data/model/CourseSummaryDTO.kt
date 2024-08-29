package com.example.fluentifyapp.data.model

data class CourseSummaryDTO(
    val courseId: Int,
    val courseName: String,
    val language: String,
    val difficulty: String,
    val noOfLessons: Int,
    val description: String
)