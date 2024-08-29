package com.example.fluentifyapp.ui.screens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.fluentifyapp.R

@Composable
fun BackButton(onBackPressed: () -> Unit,canGoBack: Boolean) {
    Image(
        painter = painterResource(id = R.drawable.backarrowgreen),
        contentDescription = "Back Button",
        modifier = Modifier
            .size(width = 24.dp, height = 23.dp)
            .clickable(enabled = canGoBack) {
                if (canGoBack) onBackPressed()
            }
            .alpha(if (canGoBack) 1f else 0.5f)

    )
}