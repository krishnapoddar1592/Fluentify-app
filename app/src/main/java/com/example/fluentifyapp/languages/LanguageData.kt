package com.example.fluentifyapp.languages

import com.example.fluentifyapp.R

object LanguageData {

    fun getLanguageList(): List<LanguageClass> {
        return listOf(
            LanguageClass("English", R.drawable.english, "🇬🇧", "EN"),
            LanguageClass("Spanish", R.drawable.spain, "🇪🇸", "ES"),
            LanguageClass("French", R.drawable.france, "🇫🇷", "FR"),
            LanguageClass("German", R.drawable.germany, "🇩🇪", "DE"),
            LanguageClass("Italian", R.drawable.italy, "🇮🇹", "IT"),
            LanguageClass("Chinese", R.drawable.china, "🇨🇳", "ZH"),
            LanguageClass("Japanese", R.drawable.japan, "🇯🇵", "JA"),
            LanguageClass("Russian", R.drawable.russia, "🇷🇺", "RU"),
            LanguageClass("Arabic", R.drawable.arabia, "🇸🇦", "AR"),
            LanguageClass("Portuguese", R.drawable.portugal, "🇵🇹", "PT"),
            LanguageClass("Dutch", R.drawable.dutch, "🇳🇱", "NL"),
            LanguageClass("Swedish", R.drawable.sweden, "🇸🇪", "SV"),
            LanguageClass("Turkish", R.drawable.turkey, "🇹🇷", "TR"),
            LanguageClass("Korean", R.drawable.korea, "🇰🇷", "KO"),
            LanguageClass("Hindi", R.drawable.india, "🇮🇳", "HI"),
            LanguageClass("Persian", R.drawable.persian, "🇮🇷", "FA"),
            LanguageClass("Vietnamese", R.drawable.vietnam, "🇻🇳", "VI"),
            LanguageClass("Thai", R.drawable.thailand, "🇹🇭", "TH")
        )
    }

    fun getLanguage(lang: String): LanguageClass? {
        return getLanguageList().find { it.text.toLowerCase() == lang.toLowerCase() }
    }
}