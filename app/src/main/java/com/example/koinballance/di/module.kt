package com.example.koinballance.di

import com.example.koinballance.component.*
import org.koin.dsl.module


val appModule = module {
    single <StoredData>{ SharedPrefsData(get()) }
    single <TransactionList>{ SimpleTransactionList(get()) }
    single <UserSettings>{ SimpleUserSettings(get(), get()) }
}