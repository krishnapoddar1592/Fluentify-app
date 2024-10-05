package com.example.fluentifyapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class GlobalAppState(
    val userId: String = "",
    val isLoggedIn: Boolean = false,
    val currentCourseId: Int = -1,
    val currentLessonId: Int = -1,
    val currentQuestionOffset: Int = 0,
    val currentLessonName: String = "",
    val currentLessonLang: String = "",
    val totalQuestions: Int = 0
)

class GlobalAppStateHolder(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("app_state", Context.MODE_PRIVATE)

    private val _state = MutableStateFlow(loadState())
    val state: StateFlow<GlobalAppState> = _state.asStateFlow()

    private fun loadState(): GlobalAppState {
        return GlobalAppState(
            userId = prefs.getString("userId", "") ?: "",
            isLoggedIn = prefs.getBoolean("isLoggedIn", false),
            currentCourseId = prefs.getInt("currentCourseId", -1),
            currentLessonId = prefs.getInt("currentLessonId", -1),
            currentQuestionOffset = prefs.getInt("currentQuestionOffset", 0),
            currentLessonName = prefs.getString("currentLessonName", "") ?: "",
            currentLessonLang = prefs.getString("currentLessonLang", "") ?: "",
            totalQuestions = prefs.getInt("totalQuestions", 0)
        )
    }

    private fun saveState(state: GlobalAppState) {
        prefs.edit {
            putString("userId", state.userId)
            putBoolean("isLoggedIn", state.isLoggedIn)
            putInt("currentCourseId", state.currentCourseId)
            putInt("currentLessonId", state.currentLessonId)
            putInt("currentQuestionOffset", state.currentQuestionOffset)
            putString("currentLessonName", state.currentLessonName)
            putString("currentLessonLang", state.currentLessonLang)
            putInt("totalQuestions", state.totalQuestions)
        }
    }

    fun updateUserId(userId: String) {
        _state.value = _state.value.copy(userId = userId, isLoggedIn = userId.isNotEmpty())
        saveState(_state.value)
    }

    fun updateCurrentLesson(
        courseId: Int,
        lessonId: Int,
        lessonName: String,
        lessonLang: String,
        totalQuestions: Int
    ) {
        _state.value = _state.value.copy(
            currentCourseId = courseId,
            currentLessonId = lessonId,
            currentLessonName = lessonName,
            currentLessonLang = lessonLang,
            totalQuestions = totalQuestions,
            currentQuestionOffset = 0
        )
        saveState(_state.value)
    }

    fun updateQuestionOffset(offset: Int) {
        _state.value = _state.value.copy(currentQuestionOffset = offset)
        saveState(_state.value)
    }

    suspend fun fetchAndUpdateLessonData(courseId: Int, lessonId: Int) {
        val lessonData = fetchLessonDataFromApi(courseId, lessonId)
        updateCurrentLesson(
            courseId = lessonData.courseId,
            lessonId = lessonData.lessonId,
            lessonName = lessonData.name,
            lessonLang = lessonData.language,
            totalQuestions = lessonData.totalQuestions
        )
    }

    private suspend fun fetchLessonDataFromApi(courseId: Int, lessonId: Int): LessonData {
        // Simulating network delay
        kotlinx.coroutines.delay(1000)
        return LessonData(courseId, lessonId, "Lesson $lessonId", "EN", 10)
    }
}


data class LessonData(
    val courseId: Int,
    val lessonId: Int,
    val name: String,
    val language: String,
    val totalQuestions: Int
)