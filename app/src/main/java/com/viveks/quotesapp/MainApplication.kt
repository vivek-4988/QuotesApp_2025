package com.viveks.quotesapp

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import com.google.firebase.FirebaseApp
import com.viveks.quotesapp.di.appModule
import com.viveks.quotesapp.remote.KoinWorkerFactory
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.get
import org.koin.core.context.GlobalContext.startKoin


class MainApplication : Application(), Configuration.Provider {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        startKoin {
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }

    override val workManagerConfiguration: Configuration
        get() {
            return Configuration.Builder()
                .setWorkerFactory(KoinWorkerFactory(get()))
                .setMinimumLoggingLevel(Log.DEBUG)
                .build()
        }
}

