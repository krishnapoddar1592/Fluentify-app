package com.example.fluentifyapp.ui.screens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.fluentifyapp.ui.theme.borderColor

@Composable
fun LanguageCard(language: String,  flagResId: Int) {
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