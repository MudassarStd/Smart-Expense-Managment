package com.example.seniorsprojectui.backend

data class Transaction(
    val time : String,
    val date : String,
    val amount : String,
    val category : String,
    val wallet : String,
    val description : String,
    val attachmentStatus : String,
    val transactionType : String
)
