package com.example.fluentifyapp.ui.viewmodel.signup

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fluentifyapp.languages.LanguageClass
import com.example.fluentifyapp.languages.LanguageData
import com.example.fluentifyapp.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*
import javax.inject.Inject


@HiltViewModel
class UserDetailsScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository
) :ViewModel(){

    // State flows for the UI
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _dob = MutableStateFlow("")
    val dob: StateFlow<String> = _dob

    private val _selectedLanguage = MutableStateFlow(LanguageClass())
    val selectedLanguage: StateFlow<LanguageClass> = _selectedLanguage

    // Language list
    val languages = LanguageData.getLanguageList()

    // State for dropdown visibility
    private val _isLanguageDropdownExpanded = MutableStateFlow(false)
    val isLanguageDropdownExpanded: StateFlow<Boolean> = _isLanguageDropdownExpanded

    fun setSelectedLanguage(language: LanguageClass) {
        _selectedLanguage.value = language
    }

    fun setLanguageDropdownExpanded(expanded: Boolean) {
        _isLanguageDropdownExpanded.value = expanded
    }

    private val _nameError = MutableStateFlow<String?>(null)
    val nameError: StateFlow<String?> = _nameError

    private val _dobError = MutableStateFlow<String?>(null)
    val dobError: StateFlow<String?> = _dobError

    fun setName(newName: String) {
        _name.value = newName
        validateName(newName)
    }
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun setDob(newDob: String) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        try {
            val selectedDate = LocalDate.parse(newDob, formatter)
            val currentDate = LocalDate.now()
            if (selectedDate.isBefore(currentDate)) {
                _dob.value = newDob
                _dobError.value = null
            } else {
                _dobError.value = "Selected date must be in the past"
            }
        } catch (e: DateTimeParseException) {
            _dobError.value = "Invalid date format"
        }
    }

    private fun validateName(name: String) {
        if (name.isBlank()) {
            _nameError.value = "Name cannot be empty"
        } else {
            _nameError.value = null
        }
    }

    fun validateInputs(): Boolean {
        validateName(_name.value)
        return _nameError.value == null && _dobError.value == null && _name.value.isNotBlank() && _dob.value.isNotBlank()
    }

    fun saveUserData() {
        viewModelScope.launch {
            if (validateInputs()) {
                _isLoading.value = true
                // ... existing saving logic ...
                _isLoading.value = false
            }
        }
    }





    fun showDatePicker(context: Context) {
        val calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Format the selected date
                val formattedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
                _dob.value = formattedDate
            },
            year, month, day
        ).show()
    }

//    fun saveUserData() {
//        viewModelScope.launch {
//            _isLoading.value = true
////
////            // Replace with actual Firebase logic
////            val user = authRepository.currentUser
////            val nameText = _name.value
////            val dobText = _dob.value
////            val language = _selectedLanguage.value
////            val email = user?.email
////
////            // Simulate saving data (Replace with actual API call)
////            if (nameText.isNotBlank() && dobText.isNotBlank() && email != null) {
////                // Here, you would send the data to your backend API
////                // ApiManager.postDataToApi(user.uid, nameText, dobText, language, email)
////
////                // Simulate network delay
////                kotlinx.coroutines.delay(1000)
////
////                // On success
////                onComplete()
////            } else {
////                // Handle error
////            }
//
//            _isLoading.value = false
//        }
//    }
}
