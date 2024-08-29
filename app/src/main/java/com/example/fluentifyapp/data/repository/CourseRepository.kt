package com.example.fluentifyapp.data.repository

import com.example.fluentifyapp.data.model.CourseSummaryDTO

interface CourseRepository {
    suspend fun getCourseSummary(): List<CourseSummaryDTO>
}