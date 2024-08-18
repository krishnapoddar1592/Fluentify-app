package com.example.fluentifyapp.ui.viewmodel.signup


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fluentifyapp.data.repository.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpScreenViewModel @Inject constructor(
    private val authRepository: AuthRepositoryImpl
) : ViewModel() {
    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible = _isPasswordVisible.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _signupResult = MutableStateFlow<SignUpResult?>(null)
    val signupResult = _signupResult.asStateFlow()

    private val _signupSuccess = MutableStateFlow(false)
    val signupSuccess = _signupSuccess.asStateFlow()

    private val _userNameError = MutableStateFlow<String?>(null)
    val userNameError: StateFlow<String?> = _userNameError

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError



    fun setUsername(value: String) {
        _username.value = value
        validateUsername(value)
    }

    fun setPassword(value: String) {
        _password.value = value
        validatePassword(value)
    }



    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }

    fun signup() {
        viewModelScope.launch {
            val email = _username.value
            val password = _password.value

            _isLoading.value = true

            if (email.isEmpty()) {
                _signupResult.value = SignUpResult.Error("Enter Email")
                _isLoading.value = false
                return@launch
            }

            if (password.isEmpty()) {
                _signupResult.value = SignUpResult.Error("Enter Password")
                _isLoading.value = false
                return@launch
            }

            try {
                val emailExists= authRepository.checkIfEmailExists(email)
                Log.d(TAG, "signUpWithEmail:$emailExists")
                val result = authRepository.createUserWithEmailAndPassword(email, password)
                result.fold(
                    onSuccess = { user ->
                        Log.d(TAG, "signUpWithEmail:success")
                        _signupResult.value = SignUpResult.Success("Successfully Signed up")
                        _signupSuccess.value = true
                        // You can call navigateToNextActivity() here if needed

                    },
                    onFailure = { exception ->
                        Log.w(TAG, "signUpWithEmail:failure", exception)
                        _signupResult.value = SignUpResult.Error("Authentication failed: ${exception.message}")
                    }
                )
            } catch (e: Exception) {
                Log.w(TAG, "signUpWithEmail:failure", e)
                _signupResult.value = SignUpResult.Error("Authentication failed: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Other functions remain the same
    fun googleSignIn() {
        // Implement Google Sign-In logic here
    }

    fun forgotPassword() {
        // Implement forgot password logic here
    }

    fun navigateToSignUp() {
        // Implement navigation to sign up screen
    }

    companion object {
        private const val TAG = "SignUpScreenViewModel"
    }


    private fun validateUsername(username: String) {
        if (username.isBlank()) {
            _userNameError.value = "Email cannot be empty"
        } else {
            viewModelScope.launch {
                try {
                    if (authRepository.checkIfEmailExists(username)) {
                        _userNameError.value = "Email already in use"
                        Log.d(TAG,"email exists")
                    } else {
                        Log.d(TAG,"email exists")
                        _userNameError.value = null
                    }
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    // Handle specific exception for badly formatted email
                    _userNameError.value = "Invalid email format"
                } catch (e: Exception) {
                    // Handle any other exceptions
                    _userNameError.value = "An error occurred: ${e.message}"
                    Log.e(TAG, "validateUsername:failure", e)
                }
            }
        }
    }

    private fun validatePassword(value: String) {
        if(value.isBlank()){
            _passwordError.value="Password cannot be empty"
        }else{
            _passwordError.value=null
        }
    }
}

sealed class SignUpResult {
    data class Success(val message: String) : SignUpResult()
    data class Error(val message: String) : SignUpResult()
}