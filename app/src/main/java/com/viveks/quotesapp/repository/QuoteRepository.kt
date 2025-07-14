package com.viveks.quotesapp.repository

import android.util.Log
import com.viveks.quotesapp.local.Quote
import com.viveks.quotesapp.local.QuoteDao
import com.viveks.quotesapp.local.QuoteEntity
import com.viveks.quotesapp.local.toEntity
import com.viveks.quotesapp.open_ai.Message
import com.viveks.quotesapp.open_ai.OpenAiApiService
import com.viveks.quotesapp.open_ai.OpenAiRequest
import com.viveks.quotesapp.remote.FirebaseQuoteDataSource
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import java.util.UUID

class QuoteRepository(
    private val quoteDao: QuoteDao,
    private val remote: FirebaseQuoteDataSource,
    private val openAi: OpenAiApiService
) {

    suspend fun generateRelatedQuote(original: Quote): Quote {
        try {
            val request = OpenAiRequest(
                messages = listOf(
                    Message("user", "Give a quote similar to: \"${original.quote}\"")
                )
            )
            val response = openAi.generateQuote(request)
            val aiQuote = response.choices.firstOrNull()?.message?.content ?: "Stay inspired."

            return Quote(
                id = UUID.randomUUID().toString(),
                quote = aiQuote,
                author = "AI",
            )
        } catch (e: HttpException) {
            // Handle HTTP-specific errors
            if (e.code() == 429) {
                Log.e("OpenAI", "Quota exceeded: ${e.message()}")
            } else {
                Log.e("OpenAI", "HTTP error: ${e.code()} ${e.message()}")
            }
        } catch (e: IOException) {
            // Network issues like timeout, no internet, etc.
            Log.e("OpenAI", "Network error: ${e.localizedMessage}")

        } catch (e: Exception) {
            // Fallback for any other unexpected errors
            Log.e("OpenAI", "Unexpected error: ${e.localizedMessage}")
        }

        return Quote(
            id = UUID.randomUUID().toString(),
            quote = "AI failed to get Quote",
            author = "AI",
        )
    }

    fun getLocalQuotes(): Flow<List<QuoteEntity>> = quoteDao.getAllQuotes()

    suspend fun insertGeneratedQuote(quote: Quote) {
        quoteDao.insertQuotes(listOf(quote.toEntity()))
    }

    suspend fun updateFavorite(id: String, isFav: Boolean) {
        quoteDao.updateFavorite(id, isFav)
    }

    suspend fun refreshQuotes() {
        val quotes = remote.fetchQuotes()
        if (quotes.isNotEmpty()) {
            quoteDao.clearQuotes()
            quoteDao.insertQuotes(quotes.map { it.toEntity() })
        }
    }
}