package com.example.fluentifyapp.ui.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fluentifyapp.data.model.HomeInfo
import com.example.fluentifyapp.data.model.UserResponse
import com.example.fluentifyapp.data.repository.AuthRepository
import com.example.fluentifyapp.data.repository.AuthRepositoryImpl
import com.example.fluentifyapp.data.repository.UserRepository
import com.example.fluentifyapp.data.repository.UserRepositoryImpl
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
    private lateinit var user: FirebaseUser
    private lateinit var uid: String
    private lateinit var userResponse: UserResponse

    private val _isLoading = MutableStateFlow(false)
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

    fun init() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Stage 1: Fetch the Firebase user
                user = authRepository.getCurrentUser() ?: throw Exception("User not logged in")
                uid = user.uid

                // Stage 2: Concurrently make the other API calls after fetching Firebase user
                val fetchUserDetails = async {
                /* another API call using uid */
                    userRepository.getHomeInfo(userId = uid)
                }

                // Await all the other API calls concurrently
                val results = awaitAll(
                    fetchUserDetails
                )
                val homeInfo = results[0] as HomeInfo

                //populate the home screen with the fetched data

                _name.value = homeInfo.name
                _currentLesson.value=homeInfo.currentLessonName
                _currentCourseName.value=homeInfo.currentCourseName
                _currentCourseProgress.value=homeInfo.courseCompletionPercentage
                _currentLessonProgress.value=homeInfo.lessonCompletionPercentage

            } catch (e: Exception) {
                // Handle any exceptions that occur during the API calls
            } finally {
                _isLoading.value = false
            }
        }
    }
}
