package com.example.koinballance

import android.app.Application
import com.example.koinballance.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApp: Application() {

    override fun onCreate() {

        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApp)
            modules(appModule)
        }
    }

}