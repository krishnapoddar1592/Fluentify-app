package com.example.fluentifyapp.ui.viewmodel.course

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.ui.input.key.Key.Companion.F
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fluentifyapp.data.model.FillQuestion
import com.example.fluentifyapp.data.model.FillQuestionResponse
import com.example.fluentifyapp.data.model.MatchQuestion
import com.example.fluentifyapp.data.model.MatchQuestionResponse
import com.example.fluentifyapp.data.model.Question
import com.example.fluentifyapp.data.repository.CourseRepository
import com.example.fluentifyapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
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

    private val _currentQuestionNo=MutableStateFlow<Int>(0)
    val currentQuestionNo=_currentQuestionNo.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex = _currentQuestionIndex.asStateFlow()

    private val _isSolved=MutableStateFlow(false)
    val isSolved=_isSolved.asStateFlow()

    private val _questionType=MutableStateFlow("")
    val questionType=_questionType.asStateFlow()

    private val _selectedWord=MutableStateFlow("")
    val selectedWord=_selectedWord.asStateFlow()

    private val _questionsAnswered=MutableStateFlow(0)
    val questionsAnsweredCount=_questionsAnswered.asStateFlow()

    private val _wordPairs=MutableStateFlow<Map<String,String>>(emptyMap())
    val wordPairs=_wordPairs.asStateFlow()

    private val _allQuestionsCompleted= MutableStateFlow(false)
    val allQuestionsCompleted=_allQuestionsCompleted.asStateFlow()

    private val _correctAnswers=MutableStateFlow(0)
    val correctAnswers=_correctAnswers.asStateFlow()

    private var currentBatchIndex = 0
    private var batchSize = 10
    private var courseId = 0 // initialize appropriately
    private var lessonId = 0 // initialize appropriately
    private var offSet=0
    private var userId=""

    fun setSelectedWord(word:String){
        _isSolved.value=true
        _selectedWord.value=word
    }

    fun setWordPairs(pairs:Map<String,String>){
        _isSolved.value=true
        _wordPairs.value=pairs
    }

    fun removeWordPairs(){
        _isSolved.value=false
        _wordPairs.value= emptyMap()
    }

    fun init(courseId: Int, lessonId: Int, userId: String,offset:Int) {
        this.courseId = courseId
        this.lessonId = lessonId
        this.offSet=offset
        this.userId=userId
        _currentQuestionNo.value=offset+1
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
        try{
            viewModelScope.launch {
                var response:Result<Unit> = Result.failure(Exception("unanswered question"))
                if(selectedWord.value!=""){
                    val currQues=_questionBatch.value[_currentQuestionIndex.value] as FillQuestion
                    if(currQues.missingWord==selectedWord.value)
                        _correctAnswers.value++
                    _questionsAnswered.value++
                     response=userRepository.answerFillQuestion(userId,courseId,lessonId,_questionBatch.value[_currentQuestionIndex.value].questionId,
                        FillQuestionResponse("Fill", _selectedWord.value)
                    )
                }

                else if(wordPairs.value.isNotEmpty()){
                    val currQues=_questionBatch.value[_currentQuestionIndex.value] as MatchQuestion
                    if(currQues.wordPairs==wordPairs.value )
                        _correctAnswers.value++
                    _questionsAnswered.value++
                    response=userRepository.answerMatchQuestion(userId,courseId,lessonId,_questionBatch.value[_currentQuestionIndex.value].questionId,
                    MatchQuestionResponse("Match", _wordPairs.value)
                    )

                }
//                else{
////                    response=Result<>(F)
//                }
                if(response.isSuccess){
                    Log.d(TAG,"Question answered successfully")
                    _isSolved.value=false
                    _selectedWord.value=""
                    _wordPairs.value= emptyMap()
                }
                else{
                    Log.e(TAG,response.toString())
                    _isSolved.value=false
                    _selectedWord.value=""
                    _wordPairs.value= emptyMap()
                }

            }
        }catch (e:Exception){
            Log.e(TAG,e.message.toString())
        }finally {

        }

        if(_currentQuestionIndex.value==_questionBatch.value.size-1)
            _allQuestionsCompleted.value=true
        else{
            _currentQuestionIndex.value += 1
            _currentQuestionNo.value+=1
            // Check if we are at the halfway point
            if (_currentQuestionIndex.value == _questionBatch.value.size / 2) {
                fetchMoreQuestions()
            }
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
