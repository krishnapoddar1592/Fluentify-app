package com.example.fluentifyapp.data.model
sealed class Question {
    abstract val questionId: Int
    abstract val questionText: String
    abstract val questionType: String
    abstract val explanation: String?
}

data class FillQuestion(
    override val questionId: Int,
    override val questionText: String,
    override val questionType: String,
    override val explanation: String?,
    val sentenceBefore: String,
    val sentenceAfter: String,
    val missingWord: String,
    val options: List<String>
) : Question()

data class MatchQuestion(
    override val questionId: Int,
    override val questionText: String,
    override val questionType: String,
    override val explanation: String?,
    val wordPairs: Map<String, String>
) : Question()
