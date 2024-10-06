package com.example.fluentifyapp.data.model

data class LessonScoreData(
    val totalQuestions: Int,
    val questionsAnswered: Int,
    val correctAnswers: Int,
    val incorrectAnswers: Int,
    val averageTime: Int = 0,
    val totalTime: Int = 0,
    val xpEarned: Int = 0,
    val totalScore: Float = 0f
)

