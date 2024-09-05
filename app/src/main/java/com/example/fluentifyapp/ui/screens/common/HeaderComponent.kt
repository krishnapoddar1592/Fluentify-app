package com.example.fluentifyapp.ui.screens.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fluentifyapp.ui.theme.AppFonts
import com.example.fluentifyapp.ui.theme.primaryColor

@Composable
fun HeaderComponent(onBackPressed: (() -> Unit)?, headerText: String, canGoBack: Boolean,fontSize: TextUnit){
    // Top bar with Back Button and Sign Up Title
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BackButton(onBackPressed,canGoBack)
        Spacer(modifier = Modifier.weight(0.5f))
        Text(
            text = headerText,
            fontSize = fontSize,
            fontFamily = AppFonts.quicksand,
            fontWeight = FontWeight.Bold,
            color = primaryColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.weight(0.6f))
    }
}