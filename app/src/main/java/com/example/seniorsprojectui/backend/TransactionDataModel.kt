package com.example.seniorsprojectui.backend

import com.example.seniorsprojectui.adapters.TransactionRVAdapter
import com.example.seniorsprojectui.fragments.TransactionFragment

class TransactionDataModel {

    companion object{
        val transactions : MutableList<Transaction> = mutableListOf()


    fun updateTrasactions(transactionData : Transaction)
    {
        transactions.add(transactionData)

//        TransactionFragment().rvAdapter.notifyDataSetChanged()

        // updating recyclerView
    }
    }
}