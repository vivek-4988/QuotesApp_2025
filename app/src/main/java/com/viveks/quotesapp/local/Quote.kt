package com.viveks.quotesapp.local

data class Quote(
    val id: String = "",
    val quote: String = "",
    val author: String = "",
    val isFavorite: Boolean = false,
)

fun QuoteEntity.toQuote(): Quote = Quote(id, quote, author, isFavorite)