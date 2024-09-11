package com.example.fluentifyapp.data.model

data class FillQuestionResponse(
    val answer_type:String,
    val selectedWord:String
)

data class MatchQuestionResponse(
    val answer_type:String,
    val wordPairs:Map<String,String>
)