package com.viveks.quotesapp.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.viveks.quotesapp.local.Quote
import kotlinx.coroutines.tasks.await

class FirebaseQuoteDataSource(
    private val firestore: FirebaseFirestore
) {
    suspend fun fetchQuotes(): List<Quote> {
        return try {
            val snapshot = firestore.collection("quotes").get().await()
            snapshot.documents.mapNotNull { it.toObject(Quote::class.java) }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}


