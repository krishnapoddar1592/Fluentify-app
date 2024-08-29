package com.example.fluentifyapp.ui.screens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fluentifyapp.data.model.CourseSummaryDTO
import com.example.fluentifyapp.ui.theme.AppFonts
import com.example.fluentifyapp.ui.theme.borderColor
import com.example.fluentifyapp.ui.theme.primaryColor
import com.example.fluentifyapp.ui.viewmodel.home.CourseSelectionScreenViewModel

@Composable
fun LanguageCard(language: String,  flagResId: Int,text:String) {
    Box(
        modifier = Modifier
            .size(106.dp, 139.dp)
            .padding(start=4.dp,end = 4.dp),
        contentAlignment = Alignment.TopCenter // Center flag horizontally
    ) {

        // Card background
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp)
            , // Add padding to make space for the flag
            elevation = CardDefaults.cardElevation(4.dp),
            shape = RoundedCornerShape(13.dp) // Optional rounded corners for card
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .border(1.dp, borderColor, RoundedCornerShape(13.dp))// Set the background color here
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = flagResId),
                        contentDescription = language,
                        modifier = Modifier.size(80.dp)
                    )
                    Text(language)
                    Text(text)
                }
            }
        }
        // Flag image, overlapping the card
        Image(
            painter = painterResource(id = flagResId),
            contentDescription = "$language flag",
            modifier = Modifier
                .size(35.dp, 20.dp)
        )
    }
}

@Composable
fun LanguageCard2(
    viewModel: CourseSelectionScreenViewModel,
    course: CourseSummaryDTO,
    flagResId: Int,
    isEnabled: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp)),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Flag image
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .aspectRatio(1.5f)
                    .padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = flagResId),
                    contentDescription = "${course.language} flag",
                    modifier = Modifier
                        .fillMaxSize()
                        .border(1.dp, Color.Black),
                    contentScale = ContentScale.FillBounds
                )
            }

            // Content
            Column(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                // Language name
                Text(
                    text = course.language,
                    fontFamily = AppFonts.quicksand,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )

                // Course details
                Column(verticalArrangement = Arrangement.Center) {
                    Text(
                        text = course.courseName,
                        fontFamily = AppFonts.rubik,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Difficulty: ${course.difficulty}",
                        fontFamily = AppFonts.quicksand,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "Lessons: ${course.noOfLessons}",
                        fontFamily = AppFonts.quicksand,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
//                    Text(
//                        text = course.description,
//                        fontFamily = AppFonts.quicksand,
//                        fontSize = 12.sp,
//                        maxLines = 2,
//                        overflow = TextOverflow.Ellipsis,
//                        lineHeight = 16.sp
//                    )
                }

                // Start button
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
//                        .padding(vertical = 8.dp),
//                    contentAlignment = Alignment.Center
                    contentAlignment = Alignment.CenterStart
                ) {
                    Button(
                        onClick = { val start = viewModel.startCourse(course.courseId) },
                        modifier = Modifier
                            .fillMaxWidth(0.75f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primaryColor,
                            contentColor = Color.White
                        ),
                        enabled = isEnabled && course.noOfLessons > 0,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = if (course.noOfLessons > 0) "Start Course" else "Coming Soon",
                            fontFamily = AppFonts.quicksand,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}