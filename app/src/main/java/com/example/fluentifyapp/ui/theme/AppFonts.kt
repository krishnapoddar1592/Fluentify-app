package com.example.fluentifyapp.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.fluentifyapp.R

object AppFonts {
    val quicksand = FontFamily(
        Font(resId = R.font.quicksand, weight = FontWeight.Normal),
        Font(resId = R.font.quicksand_bold, weight = FontWeight.Bold),
        Font(resId = R.font.quicksand_light, weight = FontWeight.Light)
    )

    val rubik = FontFamily(Font(resId = R.font.rubik_normal))

}