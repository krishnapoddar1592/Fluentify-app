package com.example.fluentifyapp.ui.screens.course

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fluentifyapp.R
import com.example.fluentifyapp.languages.LanguageData
import com.example.fluentifyapp.ui.screens.common.HeaderComponent
import com.example.fluentifyapp.ui.theme.AppFonts
import com.example.fluentifyapp.ui.theme.backgroundColor
import com.example.fluentifyapp.ui.theme.primaryColor
import kotlinx.coroutines.launch
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter.State.Empty.painter
import kotlinx.coroutines.delay
import kotlin.math.floor


@Composable
fun QuestionScreen() {

}
//@Preview
@Composable
fun FillQuestionScreen() {

    var secondsRemaining by remember { mutableFloatStateOf(15f) }
    var isTimerRunning by remember { mutableStateOf(true) }

    val progress by animateFloatAsState(
        targetValue = secondsRemaining/ 15,
        animationSpec = tween(durationMillis = 100)
    )

    LaunchedEffect(key1 = secondsRemaining, key2 = isTimerRunning) {
        if (isTimerRunning) {
            if (secondsRemaining > 0) {
                delay(100L)
                secondsRemaining-=0.1f
            } else {
                isTimerRunning = false
//                onTimerEnd()
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor),
//            .verticalScroll(rememberScrollState()),
//        verticalArrangement = Arrangement.SpaceEvenly,
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
                    onBackPressed = null,
                    headerText = "Essentials ${LanguageData.getLangEmoji("French")}",
                    canGoBack = true,
                    fontSize = 20.sp,
                    isColorWhite=true
                )
            }

            Box(
                modifier = Modifier.size(323.dp, 244.dp),
                contentAlignment = Alignment.TopCenter
            ){
                // Question Box
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 44.dp)
                ) {
                    // Apply the shadow using a Box with an offset
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .offset(y = 2.dp) // Offset shadow to the bottom
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
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically){
                                Text(
                                    text = "Question ",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = AppFonts.quicksand,
                                    color = primaryColor
                                )
                                Text(
                                    text = "7/12",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = AppFonts.quicksand,
                                    color = primaryColor
                                )


                            }

                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = "Which of the following phrases is commonly used to greet someone in Italian?",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center,
                                fontFamily = AppFonts.quicksand,
                                color = Color.Black,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                }
                // Time Circular Dial and Question Box
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(88.dp)
                        .clip(CircleShape)
                        .background(Color.White)

                ) {
                    CircularProgressIndicator(
                        progress = progress,
                        color = Color(0xFF00CED1),
                        trackColor = Color.White,
                        strokeWidth = 5.dp,
                        modifier = Modifier.size(79.dp)
                    )
                    Text(
                        text = floor(secondsRemaining).toInt().toString(),
                        color = primaryColor,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = AppFonts.quicksand
                    )
                }


            }



        }

        // State to track the selected option by index (initially -1, meaning none is selected)
        var selectedIndex by remember { mutableStateOf(-1) }

        // List of option texts
        val options = listOf("Bon appétit", "Buon giorno", "Merci beaucoup", "Adiós")
        Column(
            modifier = Modifier
                .padding(top = 60.dp,bottom = 20.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            options.forEachIndexed { index, text ->
                OptionBox(
                    text = text,
                    isSelected = selectedIndex == index, // Check if this option is selected
                    onClick = {
                        selectedIndex = index // Update selected index when clicked
                    }
                )
                Spacer(modifier = Modifier.height(16.dp)) // Space between options
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.End
        ) {
           NextButton()
        }

//        Spacer(modifier = Modifier.weight(0.5f))
    }
}

@Composable
fun OptionBox(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(273.dp, 42.dp)
            .shadow(4.dp, shape = RoundedCornerShape(9.dp), clip = false)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(9.dp)
            )
            .clickable(
                onClick = onClick,
                // Disable default click indication (highlight)
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 15.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Text label of the option
            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                fontFamily = AppFonts.quicksand
            )
            Spacer(modifier = Modifier.weight(1f))

            // The clickable circle on the right
            ClickableCircle(isFilled = isSelected)
        }
    }
}

// ClickableCircle composable to display the circle
@Composable
fun ClickableCircle(isFilled: Boolean) {
    Box(
        modifier = Modifier
            .size(15.dp)
            .border(
                width = 2.dp, // Border width
                color = if (isFilled) Color(0xFF00CED1) else Color.Gray, // Border color changes based on selection
                shape = CircleShape
            )
            .background(
                color = if (isFilled) Color(0xFF00CED1) else Color.Transparent, // Fill color based on selection
                shape = CircleShape
            )
    )
}
@Preview
@Composable
fun NextButton(){
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF00CED1))
            .clickable(onClick = {}),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.next_arrow),
            contentDescription = "Back Button",
            Modifier.size(35.dp)
        )
    }

}



