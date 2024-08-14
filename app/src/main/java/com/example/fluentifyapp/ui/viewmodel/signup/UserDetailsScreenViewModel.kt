package com.example.fluentifyapp.ui.viewmodel.signup

import android.app.DatePickerDialog
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fluentifyapp.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
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

    private val _selectedLanguage = MutableStateFlow("English")
    val selectedLanguage: StateFlow<String> = _selectedLanguage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Manage Dropdown Menu visibility
    var isLanguageDropdownExpanded = false
        private set

    // List of languages
    val languages = listOf("English", "Spanish", "French")

    fun setName(newName: String) {
        _name.value = newName
    }

    fun setDob(newDob: String) {
        _dob.value = newDob
    }

    fun setSelectedLanguage(language: String) {
        _selectedLanguage.value = language
    }

    fun setLanguageDropdownExpanded(expanded: Boolean) {
        isLanguageDropdownExpanded = expanded
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

    fun saveUserData() {
        viewModelScope.launch {
            _isLoading.value = true
//
//            // Replace with actual Firebase logic
//            val user = authRepository.currentUser
//            val nameText = _name.value
//            val dobText = _dob.value
//            val language = _selectedLanguage.value
//            val email = user?.email
//
//            // Simulate saving data (Replace with actual API call)
//            if (nameText.isNotBlank() && dobText.isNotBlank() && email != null) {
//                // Here, you would send the data to your backend API
//                // ApiManager.postDataToApi(user.uid, nameText, dobText, language, email)
//
//                // Simulate network delay
//                kotlinx.coroutines.delay(1000)
//
//                // On success
//                onComplete()
//            } else {
//                // Handle error
//            }

            _isLoading.value = false
        }
    }
}
