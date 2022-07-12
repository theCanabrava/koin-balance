package com.example.koinballance

import android.app.Application
import com.example.koinballance.di.AppModule
import com.example.koinballance.di.fragmentModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

class MainApp: Application() {

    override fun onCreate() {

        super.onCreate()
        startKoin {
            fragmentFactory()
            androidLogger()
            androidContext(this@MainApp)
            modules(AppModule().module, fragmentModule)
        }
    }

}