package com.example.fluentifyapp.ui.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.fluentifyapp.languages.LanguageData
import com.example.fluentifyapp.ui.screens.common.HeaderComponent
import com.example.fluentifyapp.ui.screens.common.LanguageCard2
import com.example.fluentifyapp.ui.theme.backgroundColor
import com.example.fluentifyapp.ui.viewmodel.home.CourseSelectionScreenViewModel


@Composable
fun CourseSelectionScreen(
    viewModel: CourseSelectionScreenViewModel,
    onBackPressed: () -> Unit,
    onNavigateToHomeScreen: ()->Unit,
    canGoBack: Boolean = false,
    userId:String=""
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.init(userId)
    }
    val isLoading by viewModel.isLoading.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (isLoading) {
                ShimmerCourseScreen()
            } else {
                ActualCourseScreen(viewModel,onBackPressed,canGoBack,onNavigateToHomeScreen)
            }
        }
    }


}

@Composable
fun ShimmerCourseScreen() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(3) {
            Box(modifier = Modifier
                .size(106.dp, 139.dp)
                .background(Color.LightGray, RoundedCornerShape(13.dp))
                .shimmerLoadingAnimation()
            )
        }
    }
}

@Composable
fun ActualCourseScreen(
    viewModel: CourseSelectionScreenViewModel,
    onBackPressed: () -> Unit,
    canGoBack: Boolean,
    onNavigateToHomeScreen: () -> Unit
) {
    val courseList = viewModel.courseSummaryList.collectAsState()
    val enrollmentResult by viewModel.enrollmentResult.collectAsState()
    val isLoading by viewModel.isLoadingCourse.collectAsState()

    LaunchedEffect(enrollmentResult) {
        when (val result = enrollmentResult) {
            is CourseSelectionScreenViewModel.CourseEnrollmentResult.Success -> {
                // Navigate to the course detail screen or wherever you want to go after successful enrollment
//                navController.navigate("courseDetail/${result.courseId}")
                onNavigateToHomeScreen()
                viewModel.resetEnrollmentResult()
            }
            is CourseSelectionScreenViewModel.CourseEnrollmentResult.Error -> {
                // Show an error message to the user
                // You might want to use a Snackbar or Dialog for this
                Log.e("HomeScreenViewModel",result.message)
                viewModel.resetEnrollmentResult()
            }
            null -> {
                // No result yet, do nothing
            }
        }
    }
    if (isLoading) {
        // Show loading indicator
        CircularProgressIndicator()
    } else {
        // Your normal screen content
        // ...
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor),
//                .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                HeaderComponent(onBackPressed = onBackPressed, headerText = "Explore", canGoBack)
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
//                    .padding(top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    courseList.value.chunked(2).forEach { rowCourses ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            rowCourses.forEach { course ->
                                LanguageCard2(
                                    viewModel=viewModel,
                                    course = course,
                                    flagResId = LanguageData.getLangFlag(course.language),

//                                modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        }

    }


}
