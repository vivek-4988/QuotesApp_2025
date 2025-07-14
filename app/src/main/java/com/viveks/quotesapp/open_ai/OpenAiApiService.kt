package com.viveks.quotesapp.open_ai

import retrofit2.http.Body
import retrofit2.http.POST

interface OpenAiApiService {
    @POST("v1/chat/completions")
    suspend fun generateQuote(@Body request: OpenAiRequest): OpenAiResponse
}