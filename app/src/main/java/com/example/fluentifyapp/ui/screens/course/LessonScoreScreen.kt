package com.example.fluentifyapp.ui.screens.course

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fluentifyapp.R
import com.example.fluentifyapp.languages.LanguageData
import com.example.fluentifyapp.ui.screens.common.HeaderComponent
import com.example.fluentifyapp.ui.theme.AppFonts
import com.example.fluentifyapp.ui.theme.backgroundColor
import com.example.fluentifyapp.ui.theme.primaryColor


@Composable
fun LessonScoreScreen(
    onBackPressed: () -> Unit,
    canGoBack: Boolean = false,
    userId:String="",
    courseId:Int=-1,
    lessonId:Int=-1,
    lessonName:String="",
    lessonLang:String="",
    totalQuestions: Int,
    questionsAnswered:Int,
    correctAnswers:Int,
    incorrectAnswers:Int,
    averageTime:Int=0,
    totalTime:Int=0,
    xpEarned:Int=0,
    totalScore:Float=0f

) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
//                .padding(16.dp)
        ) {
            item{
//                if (isLoading) {
//                    ShimmerQuestionScreen()
//                } else {
                    ActualQuestionScreen(onBackPressed,lessonName,lessonLang,totalQuestions,questionsAnswered,correctAnswers,incorrectAnswers,averageTime, totalTime, xpEarned,totalScore)
//                }
            }
        }
    }

}
//
//@Composable
//fun ShimmerQuestionScreen() {
//    Box(modifier = Modifier
//        .fillMaxSize()
//        .shimmerLoadingAnimation())
//
//}

//@Preview
@Composable
fun ActualQuestionScreen(
    onBackPressed: () -> Unit,
    lessonName: String,
    lessonLang: String,
    totalQuestions: Int,
    questionsAnswered:Int,
    correctAnswers:Int,
    incorrectAnswers:Int,
    averageTime:Int=0,
    totalTime:Int=0,
    xpEarned:Int=0,
    totalScore:Float=0f
)

{
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(350.dp), contentAlignment = Alignment.BottomCenter)
        {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(bottom = 92.dp)
                    .background(
                        color = Color(0xFF00CED1),
                        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                    ),
                contentAlignment = Alignment.TopCenter
            ){
                HeaderComponent(
                    onBackPressed = onBackPressed,
                    headerText = "$lessonName ${LanguageData.getLangEmoji(lessonLang)}",
                    canGoBack = true,
                    fontSize = 20.sp,
                    isColorWhite=true
                )
            }

            TopContentBox(
                questionsAnswered = questionsAnswered,
                correctAnswers = correctAnswers,
                incorrectAnswers = incorrectAnswers,
                totalQuestions = totalQuestions
            )


        }
        StatsGrid(averageTime,totalTime,xpEarned,totalScore)
    }
}

@Composable
fun StatsGrid(averageTime: Int, totalTime: Int, xpEarned: Int, totalScore: Float) {

    Column(
        modifier = Modifier
            .padding(top = 60.dp, bottom = 20.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            resultDataBox(heading = "Average Time:", value ="${averageTime}s" , trailer ="per question" , icon = R.drawable.clock)
            resultDataBox(heading = "Accuracy: " , value =String.format("%.2f", totalScore)+"%" , trailer ="" , icon =R.drawable.target2 )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            resultDataBox(heading ="XP Earned:" , value ="$xpEarned" , trailer = "", icon =R.drawable.star )
            resultDataBox(heading = "Total time: ", value ="${totalTime}s" , trailer ="" , icon =R.drawable.timer )
        }
    }

}


val fallbackFontFamily = FontFamily.Default
@Composable
fun TopContentBox(
    questionsAnswered:Int,
    correctAnswers:Int,
    incorrectAnswers:Int,
    totalQuestions:Int
){
    val fontFamily = if (isPreview()) fallbackFontFamily else AppFonts.quicksand


    Box(
        modifier = Modifier.size(323.dp, 185.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        // Question Box
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Apply the shadow using a Box with an offset
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = 2.dp)
                    .shadow(
                        elevation = 2.dp,
                        shape = RoundedCornerShape(20.dp),
                        clip = false
                    )
            )

            // The main content box
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(20.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 20.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Questions Answered: ",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = fontFamily,
                            color = primaryColor
                        )
                        Text(
                            text = "${questionsAnswered}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = fontFamily,
                            color = primaryColor
                        )
                        Text(
                            text = "/$totalQuestions",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = fontFamily,
                            color = primaryColor
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Custom Progress Bar
                    AnimatedProgressBars(
                        correctAnswers = correctAnswers,
                        incorrectAnswers = incorrectAnswers,
                        totalQuestions = totalQuestions
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Legend
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        LegendItem(
                            color = primaryColor,
                            count = correctAnswers,
                            label = "Correct"
                        )
                        LegendItem(
                            color = Color.Red,
                            count = incorrectAnswers,
                            label = "Incorrect"
                        )
                        LegendItem(
                            color = Color.LightGray,
                            count = totalQuestions - correctAnswers - incorrectAnswers,
                            label = "Unanswered"
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun AnimatedProgressBars(
    correctAnswers: Int,
    incorrectAnswers: Int,
    totalQuestions: Int
) {
    var animationPlayed by remember { mutableStateOf(false) }
    var animatedCorrect by remember { mutableStateOf(0f) }
    var animatedIncorrect by remember { mutableStateOf(0f) }

    // Trigger animation when composable enters composition
    LaunchedEffect(key1 = Unit) {
        animate(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 800,
                easing = FastOutSlowInEasing
            )
        ) { value, _ ->
            animatedCorrect = correctAnswers * value
            animatedIncorrect = incorrectAnswers * value
        }
        animationPlayed = true
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
    ) {
        // Unanswered - bottom layer, full width
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(Color.LightGray.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
        )

        // Incorrect - middle layer
        if (incorrectAnswers > 0) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(((animatedCorrect + animatedIncorrect) / totalQuestions))
                    .height(8.dp)
                    .background(Color.Red, RoundedCornerShape(4.dp))
            )
        }

        // Correct - top layer
        if (correctAnswers > 0) {
            Box(
                modifier = Modifier
                    .fillMaxWidth((animatedCorrect / totalQuestions))
                    .height(8.dp)
                    .background(primaryColor, RoundedCornerShape(4.dp))
            )
        }
    }
}

@Composable
fun LegendItem(color: Color, count: Int, label: String) {
    val fontFamily = if (isPreview()) fallbackFontFamily else AppFonts.quicksand

    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color, CircleShape)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "$count $label",
            fontSize = 12.sp,
            fontFamily = fontFamily,
            color = Color.Black
        )
    }
}
@Composable
fun isPreview(): Boolean {
    return LocalInspectionMode.current
}


@Composable
fun resultDataBox(
    heading: String,
    value: String,
    trailer: String,
    icon: Int
) {
    val fontFamily = if (isPreview()) fallbackFontFamily else AppFonts.quicksand
    Box(
        modifier = Modifier.size(148.dp, 64.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        // Question Box
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Apply the shadow using a Box with an offset
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = 2.dp)
                    .shadow(
                        elevation = 2.dp,
                        shape = RoundedCornerShape(9.dp),
                        clip = false
                    )
            )

            // The main content box
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(9.dp)
                    ),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(start = 10.dp)) {
                    // Icon Circular Box
                    Box(
                        modifier = Modifier
                            .size(24.dp) // Size for the circular box (48.dp diameter)
                            .background(
                                color = Color(0xFFCCFBF1), // Light greenish background for the circular box
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        // Icon inside the circular box
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp), // Icon size, 66% of 48.dp (circular box)
                            tint = Color.Black // Adjust the icon color if needed
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.padding(start = 15.dp) // Adjusted padding for spacing between icon and text
                    ) {
                        // Heading
                        Text(
                            text = heading,
                            fontSize = 12.sp,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black // Adjust heading text color if needed
                        )
                        if(trailer.isEmpty()){
                            Spacer(modifier = Modifier.height(5.dp))
                        }

                        // Value (Bold)
                        Text(
                            text = value,
                            fontSize = 16.sp,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Bold,
                            color = primaryColor // Teal color for value text
                        )

                        // Trailer Text
                        Text(
                            text = trailer,
                            fontSize = 10.sp,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF5F5F5F) // Dark gray for trailer text
                        )
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun statGridPreview() {
    StatsGrid(
        averageTime = 10,
        totalTime = 100,
        xpEarned = 120,
        totalScore = 66.66f
    )
}


