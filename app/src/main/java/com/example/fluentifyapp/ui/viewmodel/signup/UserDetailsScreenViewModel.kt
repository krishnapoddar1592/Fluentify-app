package com.example.fluentifyapp.ui.viewmodel.signup

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fluentifyapp.data.model.UserRequest
import com.example.fluentifyapp.languages.LanguageClass
import com.example.fluentifyapp.languages.LanguageData
import com.example.fluentifyapp.data.repository.AuthRepository
import com.example.fluentifyapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.inject.Inject


@HiltViewModel
class UserDetailsScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) :ViewModel(){


    // State flows for the UI
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _dob = MutableStateFlow("")
    val dob: StateFlow<String> = _dob

    private val _signupResult = MutableStateFlow<SignUpResult?>(null)
    val signupResult = _signupResult.asStateFlow()

    private val _signupSuccess = MutableStateFlow(false)
    val signupSuccess = _signupSuccess.asStateFlow()

    private lateinit var _username: String
    var username:String
        get() = _username
        set(value) {
            _username = value
        };
    private lateinit var _password: String
    var password:String
        get() = _password
        set(value) {
            _password = value
        };


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
                _dobError.value = "Please select a valid date"
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

    fun saveUserData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                Log.d("UserDetailsViewModel", "Attempting to create user: $username")
                val result = authRepository.createUserWithEmailAndPassword(username, password)
                result.fold(
                    onSuccess = { user ->
                        try {
                            Log.d("UserDetailsViewModel", "User created successfully, creating user profile")
                            userRepository.createUser(
                                UserRequest(
                                    user.uid,
                                    username,
                                    _name.value,
                                    _dob.value,
                                    _selectedLanguage.value.text
                                )
                            )

                            Log.d("UserDetailsViewModel", "User profile created successfully")
                            _signupResult.value = SignUpResult.Success("Successfully Signed up")
                            withContext(Dispatchers.Main) {
                                _signupSuccess.value = true
                                Log.d("UserDetailsViewModel", "SignupSuccess set to true")
                            }
                        } catch (e: Exception) {
                            Log.e("UserDetailsViewModel", "Error creating user profile: ${e.message}")
                            _signupResult.value = SignUpResult.Error("Error creating user: ${e.message}")
                        }
                    },
                    onFailure = { exception ->
                        Log.e("UserDetailsViewModel", "Authentication failed: ${exception.message}")
                        _signupResult.value = SignUpResult.Error("Authentication failed: ${exception.message}")
                    }
                )
            } catch (e: Exception) {
                Log.e("UserDetailsViewModel", "Unexpected error: ${e.message}")
                _signupResult.value = SignUpResult.Error("Authentication failed: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
