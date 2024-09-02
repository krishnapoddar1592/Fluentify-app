package com.example.fluentifyapp.ui.screens.home


import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fluentifyapp.R
import com.example.fluentifyapp.languages.LanguageClass
import com.example.fluentifyapp.languages.LanguageData
import com.example.fluentifyapp.ui.screens.common.LanguageCard
import com.example.fluentifyapp.ui.theme.AppFonts
import com.example.fluentifyapp.ui.theme.backgroundColor
import com.example.fluentifyapp.ui.theme.borderColor
import com.example.fluentifyapp.ui.theme.primaryColor
import com.example.fluentifyapp.ui.viewmodel.home.HomeScreenViewModel
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState
import kotlinx.coroutines.delay

fun Modifier.shimmerLoadingAnimation(
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 1000,
): Modifier {
    return composed {

        val shimmerColors = listOf(
            Color.White.copy(alpha = 0.3f),
            Color.White.copy(alpha = 0.5f),
            Color.White.copy(alpha = 1.0f),
            Color.White.copy(alpha = 0.5f),
            Color.White.copy(alpha = 0.3f),
        )

        val transition = rememberInfiniteTransition(label = "")

        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = durationMillis,
                    easing = LinearEasing,
                ),
                repeatMode = RepeatMode.Restart,
            ),
            label = "Shimmer loading animation",
        )

        this.background(
            brush = Brush.linearGradient(
                colors = shimmerColors,
                start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
                end = Offset(x = translateAnimation.value, y = angleOfAxisY),
            ),
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    onNavigateToCourseRegister: (Boolean, String) -> Unit,
    onNavigateToLesson: () -> Unit
) {
    var isRefreshing by remember { mutableStateOf(false) }

    val state = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            viewModel.init()
        }
    )

    val isLoading by viewModel.isLoading.collectAsState()
    val currentCourse by viewModel.currentCourseName.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.init()
    }
    LaunchedEffect(key1 = currentCourse , key2 = !isLoading) {
        if (currentCourse.isEmpty() && !isLoading) {
            onNavigateToCourseRegister(false,viewModel.userId.value)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .pullRefresh(state)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                if (isLoading || isRefreshing) {
                    ShimmerHomeScreen()
                } else {
                    ActualHomeScreen(viewModel, onNavigateToCourseRegister,onNavigateToLesson)
                }
            }
        }

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = state,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }

    LaunchedEffect(isLoading) {
        if (!isLoading) {
            isRefreshing = false
        }
    }
}

@Composable
fun ShimmerHomeScreen() {
    Spacer(modifier = Modifier.height(25.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(82.dp)
            .background(Color.LightGray, RoundedCornerShape(8.dp))
            .shimmerLoadingAnimation()
        )
    Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(116.dp)
            .background(Color.LightGray, RoundedCornerShape(17.dp))
            .shimmerLoadingAnimation()
        )
    Spacer(modifier = Modifier.height(26.dp))
        Box(modifier = Modifier
            .width(200.dp)
            .height(24.dp)
            .background(Color.LightGray, RoundedCornerShape(4.dp))
            .shimmerLoadingAnimation()
        )
    Spacer(modifier = Modifier.height(16.dp))
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .background(Color.LightGray, RoundedCornerShape(17.dp))
        .shimmerLoadingAnimation()
    )
    Spacer(modifier = Modifier.height(26.dp))
    Box(modifier = Modifier
        .width(100.dp)
        .height(32.dp)
        .background(Color.LightGray, RoundedCornerShape(4.dp))
        .shimmerLoadingAnimation()
    )
    Spacer(modifier = Modifier.height(16.dp))
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
fun ActualHomeScreen(
    viewModel: HomeScreenViewModel,
    onNavigateToCourseRegister: (Boolean, String) -> Unit,
    onNavigateToLesson: () -> Unit
) {
    val name by viewModel.name.collectAsState()
    val currentLesson by viewModel.currentLesson.collectAsState()
    val currentLessonProgress by viewModel.currentLessonProgress.collectAsState()
    val currentCourse by viewModel.currentCourseName.collectAsState()
    val currentCourseProgress by viewModel.currentCourseProgress.collectAsState()
    val language by viewModel.currentCourseLanguage.collectAsState()
    val courseDescription by viewModel.currentCourseDescription.collectAsState()
    val currentLanguage by viewModel.currentLanguage.collectAsState()

    Log.d("HomeScreenViewModel","$currentCourse  ${currentCourse.isEmpty()}")

    LaunchedEffect(key1 = currentCourse) {
        if (currentCourse.isEmpty()) {
            onNavigateToCourseRegister(false,viewModel.userId.value)
        }
    }


    WelcomeBox(name)
    Spacer(modifier = Modifier.height(16.dp))
    currentLanguage?.let { CourseProgressBox(it, currentCourse, currentCourseProgress,courseDescription) }
    Spacer(modifier = Modifier.height(26.dp))
    Text("Continue Learning...", fontSize = 15.sp,fontFamily = AppFonts.quicksand, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(16.dp))
    LessonProgressBox(currentCourse, currentLesson, currentLessonProgress,onNavigateToLesson)
    Spacer(modifier = Modifier.height(26.dp))
    Text("Explore", fontSize = 26.sp, color = primaryColor, fontFamily = AppFonts.quicksand, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(16.dp))
    LanguageOptions(onNavigateToCourseRegister,viewModel)
}

@Composable
fun WelcomeBox(name: String) {
    Box(
        modifier = Modifier
            .size(392.dp, 82.dp)
            .background(backgroundColor)
            .padding(top = 16.dp)
    ) {
        Column {
            Text("Welcome,", fontSize = 20.sp, fontFamily = AppFonts.quicksand, fontWeight = FontWeight.Bold)
            Text(
                text = name,
                fontSize = 26.sp,
                color = Color(0xFF208787),
                modifier = Modifier.padding(top=6.dp),
                fontFamily = AppFonts.quicksand,
                fontWeight = FontWeight.Bold
            )
        }
        Image(
            painter = painterResource(id = R.drawable.avatar),
            contentDescription = "User",
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.TopEnd)
        )
    }
}

@Composable
fun CourseProgressBox(
    currentLanguage: LanguageClass,
    currentCourse: String,
    currentCourseProgress: Int,
    courseDescription: String
) {
    var progress by remember { mutableStateOf(0f) }

    LaunchedEffect(key1 = currentCourseProgress) {
        animate(
            initialValue = 0f,
            targetValue = currentCourseProgress.toFloat() / 100f,
//            targetValue = 90f / 100f,
            animationSpec = tween(durationMillis = 1200, easing = FastOutSlowInEasing)
        ) { value, _ ->
            progress = value
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF208787))
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(74.dp)
            ) {
                CircularProgressIndicator(
                    progress = progress,
                    color = Color.White,
                    trackColor = Color(0xFF165E5E),
                    strokeWidth = 7.dp,
                    modifier = Modifier.size(118.dp)
                )
                Text(
                    text = "${(currentCourseProgress)}%",
                    color = Color.White
                )
            }

            Column(
                modifier = Modifier
                    .size(224.dp, 116.dp)
                    .padding(16.dp)
            ) {
                Text("${currentLanguage?.text ?: "Unknown"} ${currentLanguage?.emoji ?: ""}", color = Color.White, fontSize = 20.sp, fontFamily = AppFonts.quicksand, fontWeight = FontWeight.Bold)
                Text("Course: $currentCourse", color = Color.White, fontSize = 10.sp, fontFamily = AppFonts.quicksand, fontWeight = FontWeight.Bold)
                Text(courseDescription, color = Color.White, fontSize = 10.sp, fontFamily = AppFonts.quicksand, fontWeight = FontWeight.Normal)
            }
        }
    }
}

@Composable
fun LessonProgressBox(currentCourse: String, currentLesson: String, currentLessonProgress: Int, onNavigateToLesson: () -> Unit) {
    var progress by remember { mutableStateOf(0f) }

    LaunchedEffect(key1 = currentLessonProgress) {
        animate(
            initialValue = 0f,
            targetValue = currentLessonProgress.toFloat() / 100f,
            animationSpec = tween(durationMillis = 1200, easing = FastOutSlowInEasing)
        ) { value, _ ->
            progress = value
        }
    }
    Card(
        elevation = CardDefaults.cardElevation(4.dp), // Adjust the elevation value as needed
        modifier = Modifier.fillMaxWidth().clickable { onNavigateToLesson() } ,
        shape = RectangleShape// Optional padding to prevent content clipping
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, borderColor)
//                .padding(16.dp)
                .background(Color.White)

        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row {
                    Text(
                        "Course:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        fontFamily = AppFonts.quicksand
                    )
                    Text(
                        " $currentCourse",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        fontFamily = AppFonts.quicksand,
                        color = primaryColor
                    )

                }
                Row {
                    Text(
                        "Lesson:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        fontFamily = AppFonts.quicksand
                    )
                    Text(
                        " $currentLesson",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        fontFamily = AppFonts.quicksand,
                        color = primaryColor
                    )

                }
                Text(
                    "Lesson Progress:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    fontFamily = AppFonts.quicksand
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    LinearProgressIndicator(
                        progress = progress,
                        color = primaryColor,
                        modifier = Modifier
                            .weight(1f) // This will make it take up all available horizontal space
                            .height(8.dp) // Set the height of the LinearProgressIndicator
                            .clip(RoundedCornerShape(3.dp))
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Add some spacing between the progress bar and the text if needed
                    Text(
                        text = "${(currentLessonProgress)}%",
                        fontSize = 10.sp,
                        fontFamily = AppFonts.quicksand,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun LanguageOptions(onNavigateToCourseRegister: (Boolean, String) -> Unit, viewModel: HomeScreenViewModel) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .background(backgroundColor)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .height(155.dp)
                    .fillMaxWidth()
            ) {
                val langList = LanguageData.getLanguageList()
                langList.take(3).forEach {
                    LanguageCard(language = it.text, flagResId = it.image, "")
                }
            }
        }

        TextButton(
            onClick =  { onNavigateToCourseRegister(true, viewModel.userId.value) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 8.dp)
        ) {
            Text(
                "View More...",
                color = primaryColor,
                fontSize = 14.sp,
                fontFamily = AppFonts.quicksand,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
// Step 1: Define the PreviewParameterProvider
class LanguageDataProvider : PreviewParameterProvider<LanguagePreviewData> {
    override val values = sequenceOf(
        LanguagePreviewData("Russian", R.drawable.russia),
        LanguagePreviewData("Italian", R.drawable.italy),
        LanguagePreviewData("German", R.drawable.germany)
    )
}

data class LanguagePreviewData(val language: String, val iconResId: Int)

// Step 2: Update the Preview and Composable
@Preview(showBackground = true)
@Composable
fun PreviewLanguageCard(@PreviewParameter(LanguageDataProvider::class) previewData: LanguagePreviewData) {
    LanguageCard(
        language = previewData.language,
        flagResId = previewData.iconResId,
        ""
    )
}


