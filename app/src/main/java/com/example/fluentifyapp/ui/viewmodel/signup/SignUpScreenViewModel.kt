package com.example.fluentifyapp.ui.viewmodel.signup


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fluentifyapp.repository.AuthRepository
import com.example.fluentifyapp.repository.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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

    fun setUsername(value: String) {
        _username.value = value
    }

    fun setPassword(value: String) {
        _password.value = value
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
        private const val TAG = "LoginScreenViewModel"
    }
}

sealed class SignUpResult {
    data class Success(val message: String) : SignUpResult()
    data class Error(val message: String) : SignUpResult()
}