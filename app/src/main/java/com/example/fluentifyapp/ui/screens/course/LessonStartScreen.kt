package com.example.fluentifyapp.ui.screens.course
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fluentifyapp.R
import com.example.fluentifyapp.languages.LanguageData
import com.example.fluentifyapp.ui.screens.common.HeaderComponent
import com.example.fluentifyapp.ui.screens.home.shimmerLoadingAnimation
import com.example.fluentifyapp.ui.theme.AppFonts
import com.example.fluentifyapp.ui.theme.backgroundColor
import com.example.fluentifyapp.ui.theme.primaryColor
import com.example.fluentifyapp.ui.viewmodel.course.LessonStartScreenViewModel
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonStartScreen(
    viewModel: LessonStartScreenViewModel,
    onBackPressed: () -> Unit,
    onNavigateToHomeScreen: ()->Unit,
    canGoBack: Boolean = false,
    userId:String="",
    courseId:Int=-1,
    lessonId:Int=-1
) {
    var isRefreshing by remember { mutableStateOf(false) }

    val state = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            viewModel.init(userId,courseId,lessonId)
        }
    )

    LaunchedEffect(key1 = Unit) {
        viewModel.init(userId,courseId,lessonId)
    }
    val isLoading by viewModel.isLoading.collectAsState()
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
            item{
                if (isLoading|| isRefreshing) {
                    ShimmerLessonStartScreen()
                } else {
                    ActualLessonStartScreen(viewModel,onBackPressed,canGoBack,onNavigateToHomeScreen)
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
fun ShimmerLessonStartScreen() {
    val baseSpacing = 100.dp
    val halfSpacing = baseSpacing / 2
    val quarterSpacing = baseSpacing / 4

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(backgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Top Row with Back Button and Title
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = quarterSpacing),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 24.dp, height = 23.dp)
                        .background(Color.LightGray)
                        .shimmerLoadingAnimation()
                )
                Spacer(modifier = Modifier.weight(0.5f))
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(24.dp)
                        .background(Color.LightGray)
                        .shimmerLoadingAnimation()
                )
                Spacer(modifier = Modifier.weight(0.6f))
            }

            Spacer(modifier = Modifier.height(baseSpacing))

            // Circular Image Placeholder
            Box(
                modifier = Modifier
                    .size(180.dp)
                    .background(Color.LightGray, CircleShape)
                    .shimmerLoadingAnimation()
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(halfSpacing))

            // Title Text Placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(28.dp)
                    .background(Color.LightGray)
                    .shimmerLoadingAnimation()
            )

            Spacer(modifier = Modifier.height(halfSpacing))

            // Description Text Placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(20.dp)
                    .background(Color.LightGray)
                    .shimmerLoadingAnimation()
            )

            Spacer(modifier = Modifier.height(quarterSpacing))

            // Lesson Info Row Placeholder
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = quarterSpacing),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.LightGray, shape = CircleShape)
                        .shimmerLoadingAnimation()
                )
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(20.dp)
                        .background(Color.LightGray)
                        .shimmerLoadingAnimation()
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Button Placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color.LightGray, RoundedCornerShape(8.dp))
                    .shimmerLoadingAnimation()
            )
            Spacer(modifier = Modifier.weight(0.5f))
        }
    }
}


@Composable
fun ActualLessonStartScreen(viewModel: LessonStartScreenViewModel, onBackPressed: () -> Unit, canGoBack: Boolean, onNavigateToHomeScreen: () -> Unit) {
    val baseSpacing = 100.dp
    val halfSpacing = baseSpacing / 2
    val quarterSpacing = baseSpacing / 4

    val lessonProgress by viewModel.lessonProgress.collectAsState()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
//                .padding(16.dp)
                .background(backgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HeaderComponent(onBackPressed = onBackPressed, headerText = lessonProgress.courseName , canGoBack = true,18.sp)

            Spacer(modifier = Modifier.height(baseSpacing))

            Box(
                modifier = Modifier
                    .size(180.dp)
                    .shadow(
                        elevation = 8.dp, // Creates a shadow with elevation
                        shape = CircleShape, // Ensures the shadow follows the circle shape
                        clip = false // Makes sure the shadow isn't clipped to the shape
                    )
                    .clip(CircleShape) // Clips the image to a circle shape
            ) {
                Image(
                    painter = painterResource(id = R.drawable.france_circular), // should be fetched from data store in the future
                    contentDescription = "French language image",
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape), // Ensures the image itself is circular
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(halfSpacing))

            Text(
                text = "${LanguageData.getLangEmoji(lessonProgress.language)} ${lessonProgress.lessonName}",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                fontFamily = AppFonts.quicksand,
                fontSize = 22.sp,
                lineHeight = 24.sp

            )

            Spacer(modifier = Modifier.height(halfSpacing))

            Text(
                text = "${lessonProgress.lessonDescription}",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 16.dp),
                fontSize = 16.sp,
                fontFamily = AppFonts.rubik,
                fontStyle = FontStyle.Italic
            )

            Spacer(modifier = Modifier.height(quarterSpacing))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = quarterSpacing),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                LessonInfo(icon = R.drawable.clock, text = "320 min")
                LessonInfo(icon = R.drawable.target, text = "${lessonProgress.questionsAnswered} / ${lessonProgress.totalQuestions} questions")
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(
                    text = "Resume the Lesson",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
//            Spacer(modifier = Modifier.weight(0.5f))
        }
    }
}

@Composable
fun LessonInfo(icon: Int, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            tint = Color.Gray
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}