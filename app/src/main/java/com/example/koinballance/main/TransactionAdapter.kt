package com.example.koinballance.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.koinballance.R
import com.example.koinballance.component.Transaction
import com.example.koinballance.component.UserSettings
import com.example.koinballance.databinding.TransactionCardBinding
import org.koin.java.KoinJavaComponent.inject
import java.text.SimpleDateFormat
import java.util.ArrayList

class TransactionAdapter(private val transaction: Array<Transaction>, val context: Context):
    RecyclerView.Adapter<TransactionAdapter.CardViewHolder>()
{

    val userSettings: UserSettings by inject(UserSettings::class.java)

    class CardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val binding: TransactionCardBinding = TransactionCardBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.transaction_card, parent, false)

        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.binding.value.text = userSettings.formatCurrency(transaction[position].value)
        holder.binding.date.text = SimpleDateFormat("dd/MM/yy")
            .format(transaction[position].transactionDate)
    }

    override fun getItemCount(): Int = transaction.size



}