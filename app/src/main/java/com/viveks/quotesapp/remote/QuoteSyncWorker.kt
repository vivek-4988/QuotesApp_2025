package com.viveks.quotesapp.remote

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.viveks.quotesapp.repository.QuoteRepository
import org.koin.core.Koin


class KoinWorkerFactory(
    private val koin: Koin
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            QuoteSyncWorker::class.qualifiedName -> {
                QuoteSyncWorker(
                    appContext,
                    workerParameters,
                    koin.get()
                )
            }
            else -> null
        }
    }
}


class QuoteSyncWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val quoteRepository: QuoteRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            quoteRepository.refreshQuotes()
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}