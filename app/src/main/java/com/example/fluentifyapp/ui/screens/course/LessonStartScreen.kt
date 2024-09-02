package com.example.fluentifyapp.ui.screens.course
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fluentifyapp.R
import com.example.fluentifyapp.languages.LanguageData
import com.example.fluentifyapp.ui.screens.common.BackButton
import com.example.fluentifyapp.ui.theme.AppFonts
import com.example.fluentifyapp.ui.theme.backgroundColor
import com.example.fluentifyapp.ui.theme.primaryColor

@Preview(showBackground = true)
@Composable
fun LessonStartScreen() {
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
                .padding(24.dp)
                .background(backgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = quarterSpacing),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.backarrowgreen),
                    contentDescription = "Back Button",
                    modifier = Modifier.size(width = 24.dp, height = 23.dp)
                )
                Spacer(modifier = Modifier.weight(0.5f))
                Text(
                    text = "Basic French",
                    fontSize = 20.sp,
                    color = primaryColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.weight(0.6f))
            }

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
                    painter = painterResource(id = R.drawable.france_circular),
                    contentDescription = "French language image",
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape), // Ensures the image itself is circular
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(halfSpacing))

            Text(
                text = "${LanguageData.getLangEmoji("French")} Letters Letters Letters Letters Letters",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center

            )

            Spacer(modifier = Modifier.height(halfSpacing))

            Text(
                text = "Bienvenue dans un monde de baguettes et de petits pains frais, ainsi que de café aromatique.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 16.dp),
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(quarterSpacing))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = quarterSpacing),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                LessonInfo(icon = R.drawable.clock, text = "320 min")
                LessonInfo(icon = R.drawable.target, text = "28 lessons")
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
            Spacer(modifier = Modifier.weight(0.5f))
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