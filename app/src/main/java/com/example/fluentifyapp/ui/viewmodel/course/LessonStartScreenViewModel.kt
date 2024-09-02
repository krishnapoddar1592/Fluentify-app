package com.example.fluentifyapp.ui.viewmodel.course

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fluentifyapp.data.model.CourseSummaryDTO
import com.example.fluentifyapp.data.model.LessonProgressDTO
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
class LessonStartScreenViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
    private val userRepository: UserRepository
):ViewModel(){
    private val TAG="LessonStartScreenViewModel"

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _userId=MutableStateFlow("")
    val userId=_userId.asStateFlow()

    private val _lessonId=MutableStateFlow(-1)
    val lessonId=_lessonId.asStateFlow()

    private val _courseId=MutableStateFlow(-1)
    val courseId=_courseId.asStateFlow()

    private val _lessonProgress =MutableStateFlow<LessonProgressDTO>(LessonProgressDTO(0,0,"","","","",0,0))
    val lessonProgress=_lessonProgress.asStateFlow()



    fun init(uid:String){
        _isLoading.value=true
        _userId.value=uid
        viewModelScope.launch{
            try{
                delay(3000)
                val result = userRepository.getLessonStartInfo(_userId.value,_courseId.value,_lessonId.value)
                result.onSuccess { result ->
                    _lessonProgress.value = result
                }.onFailure { error ->
                    // Handle error, maybe update a error message state
                }


            }catch (e:Exception){
                Log.e(TAG,"Error fetching data ${e.message}")
            }
            finally {
                _isLoading.value=false
            }
        }

    }



}