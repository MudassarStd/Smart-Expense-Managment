package com.example.seniorsprojectui.maindb

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity
data class MainTransactionsTable (
//    @PrimaryKey(autoGenerate = true) val Tid: Int,
    val time: String,
    val date: String,
    val month: String,
    val amount: String,
    val category: String,
    val wallet: String,
    val description: String,
    val attachmentStatus: Int,
    val transactionType: String
)

