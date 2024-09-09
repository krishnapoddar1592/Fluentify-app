package com.example.fluentifyapp.data.repository

import com.example.fluentifyapp.data.model.CourseSummaryDTO
import com.example.fluentifyapp.data.model.Question

interface CourseRepository {
    suspend fun getCourseSummary(): List<CourseSummaryDTO>
    suspend fun fetchQuestionBatch(
        courseId: Int,
        lessonId: Int,
        offset: Int = 0,
        batchIndex: Int = 0,
        batchSize: Int = 5
    ): List<Question>
}