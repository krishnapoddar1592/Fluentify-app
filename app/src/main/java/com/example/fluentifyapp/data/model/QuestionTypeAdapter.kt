package com.example.fluentifyapp.data.model
import com.google.gson.*
import java.lang.reflect.Type

class QuestionTypeAdapter : JsonDeserializer<Question> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Question {
        val jsonObject = json.asJsonObject
        val questionType = jsonObject.get("questionType").asString

        return when (questionType) {
            "Fill" -> context.deserialize<FillQuestion>(jsonObject, FillQuestion::class.java)
            "Match" -> context.deserialize<MatchQuestion>(jsonObject, MatchQuestion::class.java)
            else -> throw JsonParseException("Unknown question type: $questionType")
        }
    }
}
