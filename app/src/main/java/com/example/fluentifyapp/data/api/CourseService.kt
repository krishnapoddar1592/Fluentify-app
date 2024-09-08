package com.example.fluentifyapp.data.api

import com.example.fluentifyapp.data.model.CourseSummaryDTO
import com.example.fluentifyapp.data.model.Question
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CourseService {
    @GET("courses/summary")
    suspend fun getCoursesSummary(): List<CourseSummaryDTO>

    @GET("courses/{courseId}/lessons/{lessonId}/questions/batch")
    suspend fun fetchQuestionBatch(
        @Path("courseId") courseId: Int,
        @Path("lessonId") lessonId: Int,
        @Query("offset") offset: Int = 0,
        @Query("batchIndex") batchIndex: Int = 0,
        @Query("batchSize") batchSize: Int = 5
    ): List<Question>


}