package com.example.fluentifyapp.ui.viewmodel.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fluentifyapp.data.model.HomeInfo
import com.example.fluentifyapp.data.model.UserResponse
import com.example.fluentifyapp.data.repository.AuthRepository
import com.example.fluentifyapp.data.repository.AuthRepositoryImpl
import com.example.fluentifyapp.data.repository.UserRepository
import com.example.fluentifyapp.data.repository.UserRepositoryImpl
import com.example.fluentifyapp.languages.LanguageClass
import com.example.fluentifyapp.languages.LanguageData
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val authRepository: AuthRepositoryImpl,
    private val userRepository: UserRepositoryImpl,
) : ViewModel() {
    private val TAG = "HomeScreenViewModel"
    private lateinit var user: FirebaseUser
    private lateinit var uid: String
    private lateinit var userResponse: UserResponse

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _currentCourseLanguage = MutableStateFlow("")
    val currentCourseLanguage = _currentCourseLanguage.asStateFlow()

    private val _currentCourseName = MutableStateFlow("")
    val currentCourseName = _currentCourseName.asStateFlow()

    private val _currentCourseProgress = MutableStateFlow(0)
    val currentCourseProgress = _currentCourseProgress.asStateFlow()

    private val _currentLesson = MutableStateFlow("")
    val currentLesson = _currentLesson.asStateFlow()

    private val _currentLessonProgress = MutableStateFlow(0)
    val currentLessonProgress = _currentLessonProgress.asStateFlow()

    private val _currentCourseDescription = MutableStateFlow("")
    val currentCourseDescription = _currentCourseDescription.asStateFlow()

    private val _currentLanguage = MutableStateFlow<LanguageClass?>(null)
    val currentLanguage = _currentLanguage.asStateFlow()

    fun init() {
        Log.d(TAG, "init: Starting initialization")
        _isLoading.value = true
        viewModelScope.launch {
            try {
                Log.d(TAG, "init: Fetching current user")
                user = authRepository.getCurrentUser() ?: throw Exception("User not logged in")
                uid = user.uid
                Log.d(TAG, "init: User fetched, UID: $uid")

                Log.d(TAG, "init: Fetching home info")
                val homeInfo = userRepository.getHomeInfo(userId = uid)
                Log.d(TAG, "init: Home info fetched: $homeInfo")

                // Populate the home screen with the fetched data
                _name.value = homeInfo.name
                Log.d(TAG, "init: Name updated: ${_name.value}")

                _currentLesson.value = homeInfo.currentLessonName ?: ""
                Log.d(TAG, "init: Current lesson updated: ${_currentLesson.value}")

                _currentCourseName.value = homeInfo.currentCourseName ?: ""
                Log.d(TAG, "init: Current course name updated: ${_currentCourseName.value}")

                _currentCourseProgress.value = homeInfo.courseCompletionPercentage
                Log.d(TAG, "init: Current course progress updated: ${_currentCourseProgress.value}")

                _currentLessonProgress.value = homeInfo.lessonCompletionPercentage
                Log.d(TAG, "init: Current lesson progress updated: ${_currentLessonProgress.value}")

                _currentCourseDescription.value = homeInfo.currentCourseDescription ?: ""
                Log.d(TAG, "init: Current course description updated: ${_currentCourseDescription.value}")

                _currentLanguage.value = LanguageData.getLanguage(homeInfo.courseLanguage ?: "")
                Log.d(TAG, "init: Current language updated: ${_currentLanguage.value}")



            } catch (e: Exception) {
                Log.e(TAG, "init: Error occurred", e)
                // Handle any exceptions that occur during the API calls
            } finally {
                _isLoading.value = false
                Log.d(TAG, "init: Initialization completed, isLoading set to false")
            }
        }
    }
}
