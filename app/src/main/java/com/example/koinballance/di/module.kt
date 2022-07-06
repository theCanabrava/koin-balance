package com.example.koinballance.di

import android.content.Context
import com.example.koinballance.R
import com.example.koinballance.component.*
import org.koin.core.module.Module
import org.koin.dsl.module
import java.util.*
import kotlin.collections.ArrayList

fun generateModule(context: Context): Module
{
    val appModule = module {
        single<TransactionList> { SimpleTransactionList(ArrayList<Transaction>()) }
        single<UserSettings> {
            SimpleUserSettings(
                Settings(context.getString(R.string.default_name),
                    Currency.getInstance(context.getString(R.string.default_currency)))
            )
        }
    }

    return appModule
}
