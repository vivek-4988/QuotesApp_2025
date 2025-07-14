package com.viveks.quotesapp.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {
    @Query("SELECT * FROM quotes")
    fun getAllQuotes(): Flow<List<QuoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuotes(quotes: List<QuoteEntity>)

    @Query("UPDATE quotes SET isFavorite = :isFav WHERE id = :id")
    suspend fun updateFavorite(id: String, isFav: Boolean)

    @Delete
    suspend fun deleteQuote(quote: QuoteEntity)

    @Query("DELETE FROM quotes")
    suspend fun clearQuotes()
}