package com.example.koinballance.di

import com.example.koinballance.component.*
import com.example.koinballance.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    single <StoredData>{ SharedPrefsData(get()) }
    single <TransactionList>{ SimpleTransactionList(get()) }
    single <UserSettings>{ SimpleUserSettings(get(), get()) }
    viewModel<MainViewModel> { MainViewModel(get(), get()) }
}