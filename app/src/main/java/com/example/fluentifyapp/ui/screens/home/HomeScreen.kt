package com.example.fluentifyapp.ui.screens.home


import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
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
fun HomeScreen() {
    var isLoading by remember { mutableStateOf(true) }
    val name = "Krishna Poddar"

    LaunchedEffect(key1 = true) {
        delay(5000) // 5 seconds delay
        isLoading = false
    }

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
                ShimmerHomeScreen()
            } else {
                ActualHomeScreen(name)
            }
        }
    }
}

@Composable
fun ShimmerHomeScreen() {
    Spacer(modifier = Modifier.height(25.dp))
//    ShimmerEffect {
        // Placeholder for WelcomeBox
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(82.dp)
            .background(Color.LightGray, RoundedCornerShape(8.dp))
            .shimmerLoadingAnimation()
        )
//    }
    Spacer(modifier = Modifier.height(16.dp))
//    ShimmerEffect {
        // Placeholder for SpanishProgressBox
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(116.dp)
            .background(Color.LightGray, RoundedCornerShape(17.dp))
            .shimmerLoadingAnimation()
        )
//    }
    Spacer(modifier = Modifier.height(26.dp))
//    ShimmerEffect {
        // Placeholder for "Continue Learning..." text
        Box(modifier = Modifier
            .width(200.dp)
            .height(24.dp)
            .background(Color.LightGray, RoundedCornerShape(4.dp))
            .shimmerLoadingAnimation()
        )
//    }
    Spacer(modifier = Modifier.height(16.dp))
//    ShimmerEffect {
        // Placeholder for CuisineProgressBox
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(Color.LightGray, RoundedCornerShape(17.dp))
            .shimmerLoadingAnimation()
        )
//    }
    Spacer(modifier = Modifier.height(26.dp))
//    ShimmerEffect {
        // Placeholder for "Explore" text
        Box(modifier = Modifier
            .width(100.dp)
            .height(32.dp)
            .background(Color.LightGray, RoundedCornerShape(4.dp))
            .shimmerLoadingAnimation()
        )
//    }
    Spacer(modifier = Modifier.height(16.dp))
//    ShimmerEffect {
        // Placeholder for LanguageOptions
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
//    }
}

@Composable
fun ShimmerEffect(content: @Composable () -> Unit) {
    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val shimmerColorShades = listOf(
        Color.LightGray.copy(0.9f),
        Color.LightGray.copy(0.2f),
        Color.LightGray.copy(0.9f)
    )

    val brush = Brush.linearGradient(
        colors = shimmerColorShades,
        start = Offset(translateAnim, translateAnim),
        end = Offset(translateAnim + 100f, translateAnim + 100f),
        tileMode = TileMode.Mirror
    )

    Box(modifier = Modifier
        .fillMaxWidth()
        .background(brush)
    ) {
        content()
    }
}

@Composable
fun ActualHomeScreen(name: String) {
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
                Text("Spanish 🇪🇸", color = Color.White, fontSize = 20.sp, fontFamily = AppFonts.quicksand, fontWeight = FontWeight.Bold )
                Text("Course: Culture", color = Color.White,fontSize = 10.sp, fontFamily = AppFonts.quicksand, fontWeight = FontWeight.Bold)
                Text("Culture at your fingertips*", color = Color.White, fontSize = 10.sp, fontFamily = AppFonts.quicksand, fontWeight = FontWeight.Normal)
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
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ){
            Column {
                Text("Course: Culture", fontWeight = FontWeight.Bold, fontSize = 15.sp,fontFamily = AppFonts.quicksand)
                Text("Lesson: Cuisine", fontWeight = FontWeight.Bold, fontSize = 15.sp,fontFamily = AppFonts.quicksand)
                Text("Lesson Progress:", fontWeight = FontWeight.Bold, fontSize = 15.sp,fontFamily = AppFonts.quicksand)
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment =Alignment.CenterVertically){
                    LinearProgressIndicator(
                        progress = 0.7f,
                        color = primaryColor, // Set color to the primary color
                        modifier = Modifier
                            .size(142.dp, 8.dp) // Set the size of the progress bar
                            .clip(RoundedCornerShape(3.dp)) // Round the corners by 3 dp
                    )
                    Text("70%", fontSize = 10.sp,fontFamily = AppFonts.quicksand, fontWeight = FontWeight.Bold)

                }



            }
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Food and Drink",
                modifier = Modifier.size(87.dp)
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
