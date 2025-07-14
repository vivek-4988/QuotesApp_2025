package com.viveks.quotesapp.local

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class JsonQuoteLoader(private val context: Context) {

    fun loadQuotes(): List<Quote> {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val type = Types.newParameterizedType(List::class.java, Quote::class.java)
        val adapter = moshi.adapter<List<Quote>>(type)

        val json = context.assets.open("quotes.json").bufferedReader().use { it.readText() }
        return adapter.fromJson(json) ?: emptyList()
    }
}