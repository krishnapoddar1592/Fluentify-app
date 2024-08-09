package com.example.fluentifyapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fluentifyapp.repository.AuthRepository
import com.example.fluentifyapp.repository.AuthRepositoryImpl
import com.example.fluentifyapp.repository.AuthRepositoryModule
import com.example.fluentifyapp.ui.screens.home.HomeScreen
import com.example.fluentifyapp.ui.screens.login.LoginScreen
import com.example.fluentifyapp.ui.viewmodel.login.LoginScreenViewModel
import com.example.fluentifyapp.ui.viewmodel.login.LoginScreenViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "splash") {
                        composable("splash") {
                            LaunchedEffect(Unit) {
                                val currentUser = mAuth.currentUser
                                if (currentUser == null) {
                                    Toast.makeText(this@MainActivity, "user not detected", Toast.LENGTH_SHORT).show()
                                    navController.navigate("login")
                                } else {
                                    navController.navigate("welcome")
                                }
                            }
                        }
                        composable("login") {
                            val viewModel: LoginScreenViewModel = hiltViewModel()

                            LoginScreen(
                                navController = navController,
                                viewModel = viewModel,
                                onNavigateToSignUp = { navController.navigate("signup") },
                                onNavigateAfterLogin = { navController.navigate("welcome") }
                            )
                        }
                        composable("signup") {
                            // Signup screen composable
                        }
                        composable("welcome") {
                            HomeScreen(navController)
                        }
                    }
                }
            }
        }
    }
}