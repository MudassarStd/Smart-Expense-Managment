package com.example.seniorsprojectui.backend

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Transaction(
    @PrimaryKey(autoGenerate = true) val Tid: Int,
    val time : String,
    val date : String,
    val month : String,
    val amount : String,
    val category : String,
    val wallet : String,
    val description : String,
    val attachmentStatus : String,
    val transactionType : String
)

data class CategoryData(
    val categoryLabel : String,
    val categoryIcon : Int
)

data class FinancialReportCategoryData(
    val category : String,
    val totalAmount : Double,
    val transactionType: String
)

@Entity
data class BudgetCategory(
    @PrimaryKey(autoGenerate = true) val Budgetid: Int,
    val category : String,
    val totalAmount : String,
    val month : String
)