package com.example.fluentifyapp.languages

import com.example.fluentifyapp.R


object LanguageData {

    fun getLanguageList(): List<LanguageClass> {
        val languageList = mutableListOf<LanguageClass>()

        // Default initialization of Language objects
        val english = LanguageClass().apply {
            text = "English"
            image = R.drawable.english
        }

        val spanish = LanguageClass().apply {
            text = "Spanish"
            image = R.drawable.spain
        }

        val french = LanguageClass().apply {
            text = "French"
            image = R.drawable.france
        }

        val german = LanguageClass().apply {
            text = "German"
            image = R.drawable.germany
        }

        val italian = LanguageClass().apply {
            text = "Italian"
            image = R.drawable.italy
        }

        val chinese = LanguageClass().apply {
            text = "Chinese"
            image = R.drawable.china
        }

        val japanese = LanguageClass().apply {
            text = "Japanese"
            image = R.drawable.japan
        }

        val russian = LanguageClass().apply {
            text = "Russian"
            image = R.drawable.russia
        }

        val arabic = LanguageClass().apply {
            text = "Arabic"
            image = R.drawable.arabia
        }

        val portuguese = LanguageClass().apply {
            text = "Portuguese"
            image = R.drawable.portugal
        }

        val dutch = LanguageClass().apply {
            text = "Dutch"
            image = R.drawable.dutch
        }

        val swedish = LanguageClass().apply {
            text = "Swedish"
            image = R.drawable.sweden
        }

        val turkish = LanguageClass().apply {
            text = "Turkish"
            image = R.drawable.turkey
        }

        val korean = LanguageClass().apply {
            text = "Korean"
            image = R.drawable.korea
        }

        val hindi = LanguageClass().apply {
            text = "Hindi"
            image = R.drawable.india
        }

        val persian = LanguageClass().apply {
            text = "Persian"
            image = R.drawable.persian
        }

        val vietnamese = LanguageClass().apply {
            text = "Vietnamese"
            image = R.drawable.vietnam
        }

        val thai = LanguageClass().apply {
            text = "Thai"
            image = R.drawable.thailand
        }

        // Adding Language objects to the list
        languageList.addAll(
            listOf(
                english, spanish, french, german, italian, chinese, japanese, russian, arabic,
                portuguese, dutch, swedish, turkish, korean, hindi, persian, vietnamese, thai
            )
        )

        return languageList
    }
}
