package com.example.koinballance.di

import com.example.koinballance.component.MockData
import com.example.koinballance.component.StoredData
import org.koin.dsl.module

val testModule = module {
    single<StoredData> { MockData() }
}