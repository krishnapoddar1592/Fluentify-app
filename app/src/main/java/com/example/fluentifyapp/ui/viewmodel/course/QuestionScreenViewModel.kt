package com.example.fluentifyapp.ui.viewmodel.course

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fluentifyapp.data.model.Question
import com.example.fluentifyapp.data.repository.CourseRepository
import com.example.fluentifyapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class QuestionScreenViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val TAG = "QuestionScreenViewModel"

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _questionBatch = MutableStateFlow<List<Question>>(emptyList())
    val questionBatch = _questionBatch.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex = _currentQuestionIndex.asStateFlow()

    private var currentBatchIndex = 0
    private var batchSize = 10
    private var courseId = 0 // initialize appropriately
    private var lessonId = 0 // initialize appropriately
    private var offSet=0
    private var userId=""

    fun init(courseId: Int, lessonId: Int, userId: String,offset:Int) {
        this.courseId = courseId
        this.lessonId = lessonId
        this.offSet=offset
        this.userId=userId
        loadInitialQuestions()
    }

    private fun loadInitialQuestions() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val questions = courseRepository.fetchQuestionBatch(
                    courseId = courseId,
                    lessonId = lessonId,
                    offset = offSet,
                    batchIndex = currentBatchIndex,
                    batchSize = batchSize
                )
                _questionBatch.value = questions
            } catch (e: Exception) {
                Log.e(TAG, "Failed to load questions: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onQuestionAnswered() {
        _currentQuestionIndex.value += 1

        // Check if we are at the halfway point
        if (_currentQuestionIndex.value == _questionBatch.value.size / 2) {
            fetchMoreQuestions()
        }
    }

    private fun fetchMoreQuestions() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                currentBatchIndex++
                val moreQuestions = courseRepository.fetchQuestionBatch(
                    courseId = courseId,
                    lessonId = lessonId,
                    offset = offSet,
                    batchIndex = currentBatchIndex,
                    batchSize = batchSize
                )
                // Append new questions to the existing batch
                _questionBatch.value += moreQuestions
            } catch (e: Exception) {
                Log.e(TAG, "Failed to load more questions: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
