package com.viveks.quotesapp.di

import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import com.viveks.quotesapp.local.QuoteDatabase
import com.viveks.quotesapp.open_ai.provideOpenAiApi
import com.viveks.quotesapp.remote.FirebaseQuoteDataSource
import com.viveks.quotesapp.repository.QuoteRepository
import com.viveks.quotesapp.ui.theme.quotes.QuotesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // Firestore instance — THIS WAS MISSING
    single { FirebaseFirestore.getInstance() }

    single {
        Room.databaseBuilder(get(), QuoteDatabase::class.java, "quote_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<QuoteDatabase>().quoteDao() }
    // Data Source and Repository
    single { FirebaseQuoteDataSource(get()) }

    single { QuoteRepository(get(), get(),get()) }

    //single { JsonQuoteLoader(androidContext()) }

    viewModel { QuotesViewModel(get()) }


    single { provideOpenAiApi() }

}