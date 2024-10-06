package com.example.fluentifyapp.ui.viewmodel.course

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.LinkedList
import javax.inject.Inject


@HiltViewModel
class QuestionScreenViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val TAG = "QuestionScreenViewModel"

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _secondsRemaining = MutableStateFlow(15f) // Mutable StateFlow to track seconds remaining
    val secondsRemaining: StateFlow<Float> = _secondsRemaining.asStateFlow() // Public read-only StateFlow

    private val _isTimerRunning = MutableStateFlow(true) // Mutable StateFlow to track if timer is running
    val isTimerRunning: StateFlow<Boolean> = _isTimerRunning.asStateFlow()

    private val _totalTimeTaken=MutableStateFlow(0)
    val totalTimeTaken=_totalTimeTaken.asStateFlow()

    private val _selectedIndex=MutableStateFlow(-1)
    val selectedIndex=_selectedIndex.asStateFlow()

    fun setSelectedIndex(index:Int){
        _selectedIndex.value=index
    }


    private val _wordMap = MutableStateFlow<Map<String, String>>(emptyMap())
    val wordMap = _wordMap.asStateFlow()

    // Add function to reset wordMap
    fun resetWordMap(translatedWords: List<String>, originalWords: List<String>) {
        val newWordMap = mutableMapOf<String, String>()
        translatedWords.forEach { newWordMap[it] = "" }
        originalWords.forEach { newWordMap[it] = "" }
        _wordMap.value = newWordMap
    }

    // Add function to update wordMap
    fun updateWordMap(firstWord: String, secondWord: String) {
        val currentMap = _wordMap.value.toMutableMap()
        currentMap[firstWord] = secondWord
        currentMap[secondWord] = firstWord
        _wordMap.value = currentMap
    }

    // Add function to clear word pair
    fun clearWordPair(word: String) {
        val currentMap = _wordMap.value.toMutableMap()
        val pairedWord = currentMap[word] ?: return
        currentMap[word] = ""
        currentMap[pairedWord] = ""
        _wordMap.value = currentMap
    }

    // Function to update secondsRemaining (for example, called on a timer tick)
    fun updateSecondsRemaining(seconds: Float) {
        _secondsRemaining.value = seconds
    }

    // Function to start/stop the timer
    fun setIsTimerRunning(isRunning: Boolean) {
        _isTimerRunning.value = isRunning
    }

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

    private val _currentFirstWord = MutableStateFlow("")
    val currentFirstWord = _currentFirstWord.asStateFlow()

    private val _currentSecondWord = MutableStateFlow("")
    val currentSecondWord = _currentSecondWord.asStateFlow()

    private val _isOriginalWordSelected = MutableStateFlow(false)
    val isOriginalWordSelected = _isOriginalWordSelected.asStateFlow()

    private val _isTranslatedWordSelected = MutableStateFlow(false)
    val isTranslatedWordSelected = _isTranslatedWordSelected.asStateFlow()

    private val _selectedPairsCount = MutableStateFlow(0)
    val selectedPairsCount = _selectedPairsCount.asStateFlow()

    private val _currentSelectedColor = MutableStateFlow<Color?>(null)
    val currentSelectedColor = _currentSelectedColor.asStateFlow()

    private val _colorMap = MutableStateFlow<Map<String, Color>>(emptyMap())
    val colorMap = _colorMap.asStateFlow()

    private val colorQueue = LinkedList(listOf(
        Color(0xFFa5f3fc),
        Color(0xFF99f6e4),
        Color(0xFFfecdd3),
        Color(0xFFc7d2fe)
    ))

    fun updateCurrentWords(first: String, second: String) {
        _currentFirstWord.value = first
        _currentSecondWord.value = second
    }

    fun updateSelectionFlags(original: Boolean, translated: Boolean) {
        _isOriginalWordSelected.value = original
        _isTranslatedWordSelected.value = translated
    }

    fun updateSelectedPairsCount(count: Int) {
        _selectedPairsCount.value = count
    }

    fun updateCurrentSelectedColor(color: Color?) {
        _currentSelectedColor.value = color
    }

    fun updateColorMap(newMap: Map<String, Color>) {
        _colorMap.value = newMap
    }

    fun initializeWordMapForMatchQuestion() {
        viewModelScope.launch {
            val currentQuestion = _questionBatch.value[_currentQuestionIndex.value]
            if (currentQuestion is MatchQuestion) {
                val translatedWords = currentQuestion.wordPairs.keys.toList()
                val originalWords = currentQuestion.wordPairs.values.toList()
                resetWordMap(translatedWords, originalWords)

                // Reset other state variables
                _currentFirstWord.value = ""
                _currentSecondWord.value = ""
                _isOriginalWordSelected.value = false
                _isTranslatedWordSelected.value = false
                _selectedPairsCount.value = 0
                _currentSelectedColor.value = null
                _colorMap.value = emptyMap()

                // Reset color queue
                colorQueue.clear()
                colorQueue.addAll(listOf(
                    Color(0xFFa5f3fc),
                    Color(0xFF99f6e4),
                    Color(0xFFfecdd3),
                    Color(0xFFc7d2fe)
                ))
            }
        }
    }

    fun handleWordSelection(
        selectedWord: String,
        isTranslated: Boolean
    ) {
        when {
            // Case 1: Selecting the first word of a pair
            _currentFirstWord.value.isEmpty() && (_wordMap.value[selectedWord]?.isEmpty() == true) -> {
                val newColor = colorQueue.poll() ?: Color.Gray
                val updatedColorMap = _colorMap.value.toMutableMap()
                updatedColorMap[selectedWord] = newColor
                _colorMap.value = updatedColorMap
                _currentFirstWord.value = selectedWord
                _currentSecondWord.value = ""
                _isOriginalWordSelected.value = !isTranslated
                _isTranslatedWordSelected.value = isTranslated
                _currentSelectedColor.value = newColor
            }
            // Case 2: Selecting the second word of a pair
            _currentSecondWord.value.isEmpty() && ((isTranslated && _isOriginalWordSelected.value) || (!isTranslated && _isTranslatedWordSelected.value)) -> {
                if (_wordMap.value[selectedWord] != "") {
                    clearWordPair(selectedWord)
                    _colorMap.value[selectedWord]?.let { colorQueue.offer(it) }
                    val updatedColorMap = _colorMap.value.toMutableMap()
                    updatedColorMap.remove(selectedWord)
                    updatedColorMap.remove(_wordMap.value[selectedWord])
                    _colorMap.value = updatedColorMap
                }

                updateWordMap(_currentFirstWord.value, selectedWord)
                _currentSelectedColor.value?.let {
                    val updatedColorMap = _colorMap.value.toMutableMap()
                    updatedColorMap[_currentFirstWord.value] = it
                    updatedColorMap[selectedWord] = it
                    _colorMap.value = updatedColorMap
                }

                if (colorQueue.isEmpty()) {
                    setWordPairs(_wordMap.value.filter { it.value.isNotEmpty() }.toMap())
                }

                _currentFirstWord.value = ""
                _currentSecondWord.value = ""
                _isOriginalWordSelected.value = false
                _isTranslatedWordSelected.value = false
                _selectedPairsCount.value = _colorMap.value.size / 2
                _currentSelectedColor.value = null
            }
            // Case 3: Deselecting a word
            selectedWord == _currentFirstWord.value || (_wordMap.value[selectedWord]?.isNotEmpty() == true && _currentFirstWord.value.isEmpty()) -> {
                clearWordPair(selectedWord)
                _colorMap.value[selectedWord]?.let { colorQueue.offer(it) }
                val updatedColorMap = _colorMap.value.toMutableMap()
                updatedColorMap.remove(selectedWord)
                updatedColorMap.remove(_wordMap.value[selectedWord])
                _colorMap.value = updatedColorMap
                _currentFirstWord.value = ""
                _currentSecondWord.value = ""
                _isOriginalWordSelected.value = false
                _isTranslatedWordSelected.value = false
                _selectedPairsCount.value = _colorMap.value.size / 2
                _currentSelectedColor.value = null
            }
        }
    }


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
        _currentQuestionNo.value=1
        loadInitialQuestions()
    }

    private fun loadInitialQuestions() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val questions = courseRepository.fetchQuestionBatch(
                    courseId = courseId,
                    lessonId = lessonId,
                    offset = 0,
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

    private val _resetTrigger = MutableStateFlow(0)
    val resetTrigger = _resetTrigger.asStateFlow()

    fun onQuestionAnswered() {
        viewModelScope.launch {
            _totalTimeTaken.value += (15 - _secondsRemaining.value.toInt())

            try {
                // Remove the nested viewModelScope.launch
                var response: Result<Unit> = Result.failure(Exception("unanswered question"))

                // Handle Fill Question
                if (selectedWord.value != "") {
                    val currQues = _questionBatch.value[_currentQuestionIndex.value] as FillQuestion

                    if (currQues.missingWord == selectedWord.value)
                        _correctAnswers.value++
                    _questionsAnswered.value++

                    response = withContext(Dispatchers.IO) {
                        userRepository.answerFillQuestion(
                            userId,
                            courseId,
                            lessonId,
                            _questionBatch.value[_currentQuestionIndex.value].questionId,
                            FillQuestionResponse("Fill", _selectedWord.value)
                        )
                    }
                }
                // Handle Match Question
                else if (wordPairs.value.isNotEmpty()) {

                    val currQues = _questionBatch.value[_currentQuestionIndex.value] as MatchQuestion
                    val translatedWords = currQues.wordPairs.keys.toList()
                    val filteredWordPairs = wordPairs.value.filterKeys { key ->
                        translatedWords.contains(key)
                    }
                    if (currQues.wordPairs == filteredWordPairs)
                        _correctAnswers.value++
                    _questionsAnswered.value++

                    response = withContext(Dispatchers.IO) {
                        userRepository.answerMatchQuestion(
                            userId,
                            courseId,
                            lessonId,
                            _questionBatch.value[_currentQuestionIndex.value].questionId,
                            MatchQuestionResponse("Match", filteredWordPairs)
                        )
                    }
                }

                // Log the response
                if (response.isSuccess) {
                    Log.d(TAG, "Question ${_currentQuestionIndex.value} answered successfully")
                } else {
                    Log.e(TAG, "Question ${_currentQuestionIndex.value} submission failed: $response")
                }

            } catch (e: Exception) {
                Log.e(TAG, "Error submitting question ${_currentQuestionIndex.value}: ${e.message}")
            } finally {
                setIsTimerRunning(false)

                Log.d(TAG, "Processing question ${_currentQuestionIndex.value} of ${_questionBatch.value.size}")

                if (_currentQuestionIndex.value == _questionBatch.value.size - 1) {
                    _allQuestionsCompleted.value = true
                } else {
                    moveToNextQuestion()
                }
            }
        }
    }

    private suspend fun moveToNextQuestion() {
        _isSolved.value = false
        _selectedWord.value = ""
        _wordPairs.value = emptyMap()
        _wordMap.value = emptyMap()
        _selectedIndex.value = -1

        _currentQuestionIndex.value += 1
        _currentQuestionNo.value += 1

        initializeWordMapForMatchQuestion()

        // Use coroutineScope to ensure all operations complete
        coroutineScope {
            launch { delay(50); _resetTrigger.value += 1 }
            launch {
                delay(100)  // Slightly longer delay for timer
                updateSecondsRemaining(15f)
                setIsTimerRunning(true)
            }
        }

        Log.d(TAG, "Moved to next question: Index=${_currentQuestionIndex.value}, Timer=${_isTimerRunning.value}, ResetTrigger=${_resetTrigger.value}")

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
