package com.example.fluentifyapp.data.repository

import com.example.fluentifyapp.data.api.CourseService
import com.example.fluentifyapp.data.model.CourseSummaryDTO
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val courseService: CourseService
) :CourseRepository{
    override suspend fun getCourseSummary(): List<CourseSummaryDTO> {
        val response =courseService.getCoursesSummary()
        return response
    }
}