package com.example.koinballance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AddTransactionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.title = getString(R.string.add_transaction)
        setContentView(R.layout.activity_add_transaction)
    }
}