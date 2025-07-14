package com.viveks.quotesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.viveks.quotesapp.remote.QuoteSyncWorker
import com.viveks.quotesapp.ui.theme.QuotesAPPTheme
import com.viveks.quotesapp.ui.theme.quotes.QuoteScreen
import com.viveks.quotesapp.ui.theme.quotes.QuotesViewModel
import org.koin.androidx.compose.getViewModel
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val workRequest = PeriodicWorkRequestBuilder<QuoteSyncWorker>(
            1, TimeUnit.DAYS  // Repeat every 1 day
        ).setInitialDelay(3, TimeUnit.SECONDS).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "QuoteSyncWork",
            ExistingPeriodicWorkPolicy.KEEP,  // or REPLACE
            workRequest
        )
        setContent {
            QuotesAPPTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewmodel: QuotesViewModel = getViewModel()
                    QuoteScreen(viewmodel)
                }
            }
        }
    }
}