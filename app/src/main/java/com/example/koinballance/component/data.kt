package com.example.koinballance.component

import java.util.*

class Transaction constructor(
    val id: UUID,
    val created: Date,
    val transactionDate: Date,
    val value: Int
)

class TransactionFilter constructor(
    val year: Int,
    val month: Int
)

class Settings constructor(
    val name: String,
    val currency: Currency
)