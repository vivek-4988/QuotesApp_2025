package com.viveks.quotesapp.open_ai

data class OpenAiRequest(
//    val model: String = "gpt-4o-mini",
    val model: String = "gpt-3.5-turbo",
    val messages: List<Message>,
    val store: Boolean = true
)

data class Message(
    val role: String,
    val content: String
)

data class OpenAiResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: Message
)
