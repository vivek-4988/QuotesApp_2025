package com.viveks.quotesapp.ui.theme.quotes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.viveks.quotesapp.local.JsonQuoteLoader
import com.viveks.quotesapp.local.Quote
import com.viveks.quotesapp.local.toQuote
import com.viveks.quotesapp.repository.QuoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class QuotesViewModel(
//     private val loader: JsonQuoteLoader
    private val repository: QuoteRepository
) : ViewModel() {

    private val _quotes = MutableStateFlow<List<Quote>>(emptyList())
    val quotes: StateFlow<List<Quote>> = _quotes


    init {
        viewModelScope.launch {
            repository.refreshQuotes()
            repository.getLocalQuotes().collectLatest {
                _quotes.value = it.map { it.toQuote() }
            }
        }
    }

    /*
    * load from json file
    * */

    init {
//        viewModelScope.launch {
//            val quotes = loader.loadQuotes()
//            _quotes.value = quotes
//        }

        //uploadQuotesToFirestore()

//        viewModelScope.launch {
//            val quotes = repository.fetchQuotes()
//            _quotes.value = quotes
//        }
    }

    fun toggleFavorite(id: String, currentState: Boolean) {
        viewModelScope.launch {
            repository.updateFavorite(id, !currentState)
        }

        updateFavoriteCountInFirebase(id, !currentState)

    }

    fun toggleFavoriteWithAi(quote: Quote) {
        viewModelScope.launch {
            repository.updateFavorite(quote.id, !quote.isFavorite)

            if (!quote.isFavorite) {
                val aiQuote = repository.generateRelatedQuote(quote)
                if (quote.quote.isNotEmpty()) {
                    repository.insertGeneratedQuote(aiQuote)
                }
            }
        }
    }

    private fun updateFavoriteCountInFirebase(id: String, isFavorite: Boolean) {
        val docRef = FirebaseFirestore.getInstance().collection("quotes").document(id)

        Firebase.firestore.runTransaction { transaction ->
            val snapshot = transaction.get(docRef)
            val currentCount = snapshot.getLong("likes") ?: 0L
            val updatedCount = if (isFavorite) currentCount + 1 else currentCount - 1
            transaction.update(docRef, "likes", updatedCount)
        }.addOnSuccessListener {
            Log.d("FirebaseSync", "Updated likes count for $id")
        }.addOnFailureListener { e ->
            Log.e("FirebaseSync", "Failed to update likes for $id", e)
        }
    }

}