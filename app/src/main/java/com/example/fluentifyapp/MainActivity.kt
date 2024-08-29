package com.example.fluentifyapp

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fluentifyapp.ui.screens.home.CourseSelectionScreen
import com.example.fluentifyapp.ui.screens.home.HomeScreen
import com.example.fluentifyapp.ui.screens.login.LoginScreen
import com.example.fluentifyapp.ui.screens.signup.SignUpScreen
import com.example.fluentifyapp.ui.screens.signup.UserDetailsScreen
import com.example.fluentifyapp.ui.viewmodel.home.CourseSelectionScreenViewModel
import com.example.fluentifyapp.ui.viewmodel.home.HomeScreenViewModel
import com.example.fluentifyapp.ui.viewmodel.login.LoginScreenViewModel
import com.example.fluentifyapp.ui.viewmodel.signup.SignUpScreenViewModel
import com.example.fluentifyapp.ui.viewmodel.signup.UserDetailsScreenViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var mAuth: FirebaseAuth
    @RequiresApi(Build.VERSION_CODES.O)
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
                                    navController.navigate("welcome") {
                                        popUpTo(0) { inclusive = true }  // This clears the entire back stack.
                                        launchSingleTop = true  // Ensures only a single instance of the destination.
                                    }
                                }
                            }
                        }
                        composable("login") {
                            val viewModel: LoginScreenViewModel = hiltViewModel()

                            LoginScreen(
                                viewModel = viewModel,
                                onNavigateToSignUp = { navController.navigate("signup") },
                                onNavigateAfterLogin = {
                                    navController.navigate("welcome") {
                                        popUpTo(0) { inclusive = true }  // This clears the entire back stack.
                                        launchSingleTop = true  // Ensures only a single instance of the destination.
                                    }
                                }
                            )
                        }
                        composable("signup") {
                            // Signup screen composable
                            val viewModel:SignUpScreenViewModel= hiltViewModel()
                            SignUpScreen(
                                viewModel = viewModel,
                                onNavigateToUserDetails = { username, email ->
                                    navController.navigate("userDetails/$username/$email")
                                },
                                onNavigateToSignIn = { navController.navigate("login") },
                                onNavigateAfterSignUp = { username, email ->
                                    navController.navigate("userDetails/$username/$email")
                                }
                            )
                        }
                        composable("welcome") {
                            val viewmodel :HomeScreenViewModel= hiltViewModel()
                            HomeScreen(
                                viewmodel,
                                onNavigateToCourseRegister = {canGoBack,userId->
                                    navController.navigate("selectCourse/$canGoBack/$userId")
                                }
                            )
                        }
                        composable(
                            "userDetails/{username}/{password}",
                            arguments = listOf(
                                navArgument("username") { type = NavType.StringType },
                                navArgument("password") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val username = backStackEntry.arguments?.getString("username") ?: ""
                            val password = backStackEntry.arguments?.getString("password") ?: ""
                            val viewModel: UserDetailsScreenViewModel = hiltViewModel()
                            UserDetailsScreen(
                                viewModel = viewModel,
                                username = username,
                                password = password,
                                onNavigateAfterSignIn = {
                                    navController.navigate("welcome") {
                                        popUpTo(0) { inclusive = true }  // This clears the entire back stack.
                                        launchSingleTop = true  // Ensures only a single instance of the destination.
                                    } },
                                onNavigateToCourseRegister = {canGoBack,userId->navController.navigate("selectCourse/$canGoBack/$userId")},
                                onBackPressed = { navController.navigate("signup") }
                            )
                        }
                        composable("selectCourse/{canGoBack}/{userId}",
                                arguments = listOf(
                                navArgument("canGoBack") { type = NavType.BoolType} ,
                                navArgument("userId") { type = NavType.StringType }
                        ))
                        {backStackEntry->
                            val canGoBack=backStackEntry.arguments?.getBoolean("canGoBack")?:false
                            val userId=backStackEntry.arguments?.getString("userId")?:""
                            val viewModel:CourseSelectionScreenViewModel=hiltViewModel()
                            CourseSelectionScreen(

                                viewModel = viewModel,
                                onBackPressed = {navController.navigate("welcome")},
                                onNavigateToHomeScreen = {
                                    navController.navigate("welcome") {
                                        popUpTo(0) { inclusive = true }  // This clears the entire back stack.
                                        launchSingleTop = true  // Ensures only a single instance of the destination.
                                    }
                                },
                                canGoBack=canGoBack,
                                userId=userId
                            )
                        }
                    }
                }
            }
        }
    }
}