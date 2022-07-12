package com.example.koinballance.di

import com.example.koinballance.addtransaction.ConfirmTransactionFragment
import org.koin.androidx.fragment.dsl.fragment
import org.koin.dsl.module

val fragmentModule = module {
    fragment { ConfirmTransactionFragment(get()) }
}