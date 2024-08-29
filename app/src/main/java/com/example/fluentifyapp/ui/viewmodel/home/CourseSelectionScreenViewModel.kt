package com.example.fluentifyapp.ui.viewmodel.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fluentifyapp.data.model.CourseSummaryDTO
import com.example.fluentifyapp.data.repository.CourseRepository
import com.example.fluentifyapp.data.repository.UserRepository
import com.example.fluentifyapp.languages.LanguageClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CourseSelectionScreenViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
    private val userRepository: UserRepository
):ViewModel(){
    private val TAG="CourseSelectionScreenViewModel"
    private val _courseSummaryList = MutableStateFlow<List<CourseSummaryDTO>>(listOf())
    val courseSummaryList = _courseSummaryList.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _isLoadingCourse = MutableStateFlow(false)
    val isLoadingCourse = _isLoadingCourse.asStateFlow()

    private val _userId=MutableStateFlow("")
    val userId=_userId.asStateFlow()

    private val _enrollmentResult = MutableStateFlow<CourseEnrollmentResult?>(null)
    val enrollmentResult: StateFlow<CourseEnrollmentResult?> = _enrollmentResult





    fun init(uid:String){
        _isLoading.value=true
        _userId.value=uid
        viewModelScope.launch{
            try{
                delay(3000)
                _courseSummaryList.value=courseRepository.getCourseSummary()


            }catch (e:Exception){
                Log.e(TAG,"Error fetching data ${e.message}")
            }
            finally {
                _isLoading.value=false
            }
        }

    }

    fun startCourse(courseId: Int) {
        _isLoadingCourse.value = true
        viewModelScope.launch {
            try {
                val result = userRepository.enrollUserToCourse(_userId.value, courseId)
                result.fold(
                    onSuccess = {
                        delay(2000)
                        _enrollmentResult.value = CourseEnrollmentResult.Success
                    },
                    onFailure = { exception ->
                        val errorMessage = when (exception) {
                            is IOException -> "Network error. Please check your connection."
                            is HttpException -> "Server error: ${exception.code()}. Please try again later."
                            else -> "An unexpected error occurred: ${exception.message}"
                        }
                        _enrollmentResult.value = CourseEnrollmentResult.Error(errorMessage)
                        Log.e(TAG, "Error starting course: $errorMessage")
                    }
                )
            } catch (e: Exception) {
                _enrollmentResult.value = CourseEnrollmentResult.Error("An unexpected error occurred: ${e.message}")
                Log.e(TAG, "Error starting course", e)
            } finally {
                _isLoadingCourse.value = false
            }
        }
    }

    sealed class CourseEnrollmentResult {
        object Success : CourseEnrollmentResult()
        data class Error(val message: String) : CourseEnrollmentResult()
    }
    fun resetEnrollmentResult() {
        _enrollmentResult.value = null
    }


}