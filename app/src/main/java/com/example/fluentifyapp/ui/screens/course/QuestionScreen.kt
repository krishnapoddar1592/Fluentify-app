package com.example.fluentifyapp.ui.screens.course

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fluentifyapp.languages.LanguageData
import com.example.fluentifyapp.ui.screens.common.HeaderComponent
import com.google.android.play.integrity.internal.f


@Composable
fun QuestionScreen() {

}
@Preview
@Composable
fun FillQuestionScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White) // Background color 00CED1
//            .padding(top = -16.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(402.dp), contentAlignment = Alignment.BottomCenter){
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(bottom = 92.dp)
                    .background(
                        color = Color(0xFF00CED1),
                        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                    )){
            }

            Box(
                modifier = Modifier.size(323.dp, 229.dp),
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
                            .offset(y = 4.dp) // Offset shadow to the bottom
                            .shadow(
                                elevation = 4.dp,
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
                            Text(
                                text = "Question 7/12",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Which of the following phrases is commonly used to greet someone in Italian?",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center,
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
                        progress = 0.75f,
                        color = Color(0xFF00CED1),
                        trackColor = Color.White,
                        strokeWidth = 5.dp,
                        modifier = Modifier.size(79.dp)
                    )
                    Text(
                        text = "75",
                        color = Color(0xFF00CED1),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }


            }



        }

        // Header Component
//        HeaderComponent(null,"Essentials ${LanguageData.getLangEmoji("French")}",false,20.sp)

        Spacer(modifier = Modifier.weight(0.5f))

        // Answer Options
        Column(
            modifier = Modifier.padding(vertical = 60.dp).fillMaxSize().align(Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OptionBox(text = "Bon appétit")
            OptionBox(text = "Buon giorno")
            OptionBox(text = "Merci beaucoup")
            OptionBox(text = "Adiós")
        }
        Spacer(modifier = Modifier.weight(0.5f))
    }
}

@Composable
fun OptionBox(text: String) {
    Box(
        modifier = Modifier
            .size(273.dp, 42.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(9.dp)

            )
            .border(1.dp, Color.Black, shape = RoundedCornerShape(9.dp))
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(modifier = Modifier.padding(horizontal = 10.dp), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically){

            Text(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )
            Spacer(modifier = Modifier.weight(1f))
            ClickableCircle()
        }

    }
}

@Composable
fun ClickableCircle() {
    // State to track if the circle is filled or not
    var isFilled by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .size(15.dp)
            .border(
                width = 2.dp, // Border width
                color = if(isFilled) Color(0xFF00CED1) else Color.Black, // Border color
                shape = CircleShape
            )
            .background(
                color = if (isFilled) Color(0xFF00CED1) else Color.Transparent, // Fill color on click
                shape = CircleShape
            )
            .clickable {
                isFilled = !isFilled // Toggle fill state on click
            },

        contentAlignment = Alignment.Center
    ){

    }
}