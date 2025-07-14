package com.viveks.quotesapp.local

fun QuoteEntity.toDomain(): Quote = Quote(
    id = id,
    quote = quote,
    author = author,
//    likes = likes,
//    isFavorite = isFavorite,
)

fun Quote.toEntity(backgroundId: Long = 0): QuoteEntity = QuoteEntity(
    id = id,
    quote = quote,
    author = author,
//    likes = likes as Any,
    isFavorite = isFavorite,
)

