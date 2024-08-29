package com.example.fluentifyapp.data.api

import com.example.fluentifyapp.data.model.CourseSummaryDTO
import retrofit2.http.GET

interface CourseService {
    @GET("courses/summary")
    suspend fun getCoursesSummary(): List<CourseSummaryDTO>
}