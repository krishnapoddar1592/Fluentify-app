package com.example.fluentifyapp.data.model

data class HomeInfo (
    val name:String,
    val currentCourseName:String,
    val currentCourseDescription:String,
    val courseCompletionPercentage:Int,
    val currentLessonName:String,
    val lessonCompletionPercentage:Int,
    val courseId:Int,
    val lessonId:Int,
    val courseLanguage:String

)