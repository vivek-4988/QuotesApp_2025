package com.viveks.quotesapp.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes")
data class QuoteEntity(
    @PrimaryKey val id: String,
    val quote: String = "",
    val author: String = "",
    val isFavorite: Boolean = false,
)