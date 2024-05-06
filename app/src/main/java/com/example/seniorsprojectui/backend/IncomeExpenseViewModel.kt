package com.example.seniorsprojectui.backend

import androidx.lifecycle.ViewModel
import com.example.seniorsprojectui.adapters.TransactionRVAdapter

class IncomeExpenseViewModel : ViewModel() {

    private lateinit var rvAdapter: TransactionRVAdapter

    val transactions : MutableList<Transaction> = mutableListOf()

    fun updateTrasactions(transactionData : Transaction)
    {
        transactions.add(transactionData)

        // updating recyclerView
        rvAdapter.notifyDataSetChanged()
    }

}