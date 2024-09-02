package com.example.fluentifyapp.data.model

data class LessonProgressDTO(
    val lessonId: Int,
    val courseId: Int,
    val lessonName: String,
    val language: String,
    val courseName: String,
    val lessonDescription: String,
    val totalQuestions: Int,
    val questionsAnswered: Int
)