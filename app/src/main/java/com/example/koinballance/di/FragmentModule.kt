package com.example.koinballance.di

import com.example.koinballance.addtransaction.ConfirmTransactionFragment
import com.example.koinballance.main.FilterFragment
import com.example.koinballance.transaction.DeleteTransactionFragment
import org.koin.androidx.fragment.dsl.fragment
import org.koin.dsl.module

val fragmentModule = module {
    fragment { ConfirmTransactionFragment(get()) }
    fragment { FilterFragment(get()) }
    fragment { DeleteTransactionFragment(get(), get()) }
}