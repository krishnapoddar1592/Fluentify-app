package com.example.fluentifyapp.ui.screens.home


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fluentifyapp.R
import com.example.fluentifyapp.ui.theme.AppFonts
import com.example.fluentifyapp.ui.theme.backgroundColor
import com.example.fluentifyapp.ui.theme.borderColor
import com.example.fluentifyapp.ui.theme.primaryColor

//@Preview(
//    showBackground = true,
//    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO // Force light theme
//)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val name="Krishna Poddar"
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
            WelcomeBox(name)
            Spacer(modifier = Modifier.height(16.dp))
            SpanishProgressBox()
            Spacer(modifier = Modifier.height(26.dp))
            Text("Continue Learning...", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))
            CuisineProgressBox()
            Spacer(modifier = Modifier.height(26.dp))
            Text("Explore", fontSize = 26.sp, color = primaryColor, fontFamily = AppFonts.quicksand, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            LanguageOptions()
        }
    }
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
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "User",
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.TopEnd)
        )
    }
}

@Composable
fun SpanishProgressBox() {
    Box(
        contentAlignment = Alignment.Center,

        modifier = Modifier
            .fillMaxWidth()
//            .size(height = 116.dp)
            .background(Color(0xFF208787), RoundedCornerShape(17.dp))
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                contentAlignment = Alignment.Center, // Center the text in the middle of the CircularProgressIndicator
                modifier = Modifier.size(74.dp)
            ) {
                CircularProgressIndicator(
                    progress = 0.75f,
                    color = Color.White,
                    trackColor = Color.Black,
                    strokeWidth = 7.dp, // Set the thickness here
                    modifier = Modifier.size(118.dp)
                )
                Text(
                    text = "75%", // Your desired text
                    color = Color.White// Choose your preferred text style
                )
            }

            Column(
                modifier = Modifier
                    .size(224.dp, 116.dp)
                    .padding(16.dp)
            ) {
                Text("Spanish 🇪🇸", color = Color.White, fontSize = 20.sp )
                Text("Course: Culture", color = Color.White)
                Text("Culture at your fingertips*", color = Color.White)
            }
        }
    }
}

@Composable
fun CuisineProgressBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, borderColor, RoundedCornerShape(17.dp))
            .padding(16.dp)
            .background(backgroundColor)
    ) {
        Column {
            Text("Course: Culture")
            Text("Lesson: Cuisine")
            LinearProgressIndicator(
                progress = 0.7f,
                modifier = Modifier.fillMaxWidth()
            )
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Food and Drink",
                modifier = Modifier.size(50.dp)
            )
        }
    }
}

@Composable
fun LanguageOptions() {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .height(155.dp)
            .background(backgroundColor)
    ) {
        LanguageCard("Russian", R.drawable.russia)
        LanguageCard("Italian", R.drawable.italy)
        LanguageCard("German", R.drawable.germany)
        LanguageCard("German", R.drawable.germany)
        LanguageCard("German", R.drawable.germany)
        LanguageCard("German", R.drawable.germany)
        LanguageCard("German", R.drawable.germany)
    }
}

@Composable
fun LanguageCard(language: String, iconResId: Int) {
    Card(
        modifier = Modifier
            .size(106.dp, 139.dp)
            .padding(end = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp) // Optional elevation
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .border(1.dp, borderColor, RoundedCornerShape(13.dp))// Set the background color here
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center, // Center the content vertically
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = iconResId),
                    contentDescription = language,
                    modifier = Modifier.size(80.dp)
                )
                Text(language)
            }
        }
    }
}
