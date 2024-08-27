package com.example.fluentifyapp.languages
import java.io.Serializable

data class LanguageClass(
    var text: String = "",
    var image: Int = 0,
    var emoji: String = "",
    var code: String = ""
) : Serializable