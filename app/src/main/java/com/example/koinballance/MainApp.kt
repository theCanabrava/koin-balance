package com.example.koinballance

import android.app.Application
import com.example.koinballance.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

class MainApp: Application() {

    override fun onCreate() {

        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApp)
            modules(AppModule().module)
        }
    }

}