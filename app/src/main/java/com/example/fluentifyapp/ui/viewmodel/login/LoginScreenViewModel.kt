package com.example.fluentifyapp.ui.viewmodel.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fluentifyapp.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible = _isPasswordVisible.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _loginResult = MutableStateFlow<LoginResult?>(null)
    val loginResult = _loginResult.asStateFlow()

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess = _loginSuccess.asStateFlow()

    fun setUsername(value: String) {
        _username.value = value
    }

    fun setPassword(value: String) {
        _password.value = value
    }

    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }

    fun login() {
        viewModelScope.launch {
            val email = _username.value
            val password = _password.value

            _isLoading.value = true

            if (email.isEmpty()) {
                _loginResult.value = LoginResult.Error("Enter Email")
                _isLoading.value = false
                return@launch
            }

            if (password.isEmpty()) {
                _loginResult.value = LoginResult.Error("Enter Password")
                _isLoading.value = false
                return@launch
            }

            try {
                val result = authRepository.signInWithEmailAndPassword(email, password)
                result.fold(
                    onSuccess = { user ->
                        Log.d(TAG, "signInWithEmail:success")
                        _loginResult.value = LoginResult.Success("Successfully logged in")
                        _loginSuccess.value = true
                        // You can call navigateToNextActivity() here if needed

                    },
                    onFailure = { exception ->
                        Log.w(TAG, "signInWithEmail:failure", exception)
                        _loginResult.value = LoginResult.Error("Authentication failed: ${exception.message}")
                    }
                )
            } catch (e: Exception) {
                Log.w(TAG, "signInWithEmail:failure", e)
                _loginResult.value = LoginResult.Error("Authentication failed: ${e.message}")
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

sealed class LoginResult {
    data class Success(val message: String) : LoginResult()
    data class Error(val message: String) : LoginResult()
}