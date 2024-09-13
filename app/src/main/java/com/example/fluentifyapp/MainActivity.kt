package com.example.fluentifyapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fluentifyapp.ui.screens.course.LessonStartScreen
import com.example.fluentifyapp.ui.screens.course.QuestionScreen
import com.example.fluentifyapp.ui.screens.home.CourseSelectionScreen
import com.example.fluentifyapp.ui.screens.home.HomeScreen
import com.example.fluentifyapp.ui.screens.login.LoginScreen
import com.example.fluentifyapp.ui.screens.signup.SignUpScreen
import com.example.fluentifyapp.ui.screens.signup.UserDetailsScreen
import com.example.fluentifyapp.ui.viewmodel.course.LessonStartScreenViewModel
import com.example.fluentifyapp.ui.viewmodel.course.QuestionScreenViewModel
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
                                },
                                onNavigateToLesson = {canGoBack,userId,courseId,lessonId->
                                    navController.navigate("lesson/$canGoBack/$userId/$courseId/$lessonId")
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
                        composable("lesson/{canGoBack}/{userId}/{courseId}/{lessonId}",
                            arguments = listOf(
                                navArgument("canGoBack") { type = NavType.BoolType} ,
                                navArgument("userId") { type = NavType.StringType },
                                navArgument("courseId") { type = NavType.IntType},
                                navArgument("lessonId") { type = NavType.IntType }
                            ))
                        {backStackEntry->
                            val canGoBack=backStackEntry.arguments?.getBoolean("canGoBack")?:false
                            val userId=backStackEntry.arguments?.getString("userId")?:""
                            val courseId=backStackEntry.arguments?.getInt("courseId")?:-1
                            val lessonId=backStackEntry.arguments?.getInt("lessonId")?:-1
                            val viewModel:LessonStartScreenViewModel=hiltViewModel()
                            LessonStartScreen(
                                viewModel = viewModel,
                                onBackPressed = {navController.navigate("welcome")},
                                onNavigateToHomeScreen = {
                                    navController.navigate("welcome") {
                                        popUpTo(0) { inclusive = true }  // This clears the entire back stack.
                                        launchSingleTop = true  // Ensures only a single instance of the destination.
                                    }
                                },
                                canGoBack=canGoBack,
                                userId=userId,
                                courseId=courseId,
                                lessonId=lessonId,
                                onNavigateToQuestionScreen={canGoBack,userId,courseId,lessonId,questionOffset,lessonName,lessonLang,totalQuestions->
                                    navController.navigate("questionScreen/$canGoBack/$userId/$courseId/$lessonId/$questionOffset/$lessonName/$lessonLang/$totalQuestions")

                                }
                            )
                        }
                        composable(
                            route = "questionScreen/{canGoBack}/{userId}/{courseId}/{lessonId}/{questionOffset}/{lessonName}/{lessonLang}/{totalQuestions}",
                            arguments = listOf(
                                navArgument("canGoBack") { type = NavType.BoolType },
                                navArgument("userId") { type = NavType.StringType },
                                navArgument("courseId") { type = NavType.IntType },
                                navArgument("lessonId") { type = NavType.IntType },
                                navArgument("questionOffset") { type = NavType.IntType },
                                navArgument("lessonName") { type = NavType.StringType },
                                navArgument("lessonLang") { type = NavType.StringType },
                                navArgument("totalQuestions") { type = NavType.IntType }
                            )
                        ) { backStackEntry ->
                            val canGoBack = backStackEntry.arguments?.getBoolean("canGoBack") ?: false
                            val userId = backStackEntry.arguments?.getString("userId") ?: ""
                            val courseId = backStackEntry.arguments?.getInt("courseId") ?: 0
                            val lessonId = backStackEntry.arguments?.getInt("lessonId") ?: 0
                            val questionOffset = backStackEntry.arguments?.getInt("questionOffset") ?: -1
                            val lessonName = backStackEntry.arguments?.getString("lessonName") ?: ""
                            val lessonLang = backStackEntry.arguments?.getString("lessonLang") ?: ""
                            val totalQuestions = backStackEntry.arguments?.getInt("totalQuestions") ?: -1

                            Log.d("MainActivity", "QuestionScreen: userId=$userId, courseId=$courseId, lessonId=$lessonId")

                            val viewModel: QuestionScreenViewModel = hiltViewModel()

                            QuestionScreen(
                                viewModel = viewModel,
                                onBackPressed = { navController.navigate("welcome") },
                                onNavigateToHomeScreen = { navController.navigate("welcome") },
                                canGoBack = canGoBack,
                                userId = userId,
                                courseId = courseId,
                                lessonId = lessonId,
                                questionOffset = questionOffset,
                                lessonName = lessonName,
                                lessonLang = lessonLang,
                                totalQuestions = totalQuestions
                            )
                        }
                    }
                }
            }
        }
    }
}