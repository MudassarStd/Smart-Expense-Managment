package com.example.seniorsprojectui.backend

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.URI

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
    val attachment : String,
    val transactionType : String,
    val uid : Int
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
    val uid : Int,
    val category : String,
    var totalAmount : String,
    val month : String
)

@Entity
data class UserData(
    @PrimaryKey(autoGenerate = true)
    val uid : Int,
    val username : String,
    val useremail : String,
    val userpassword : String
)

data class MyWalletsDC(
    val userid : Int,
    val walletName: String,
    val walletAmount : String
)

data class TransDummy(val id: Int, val type: String, val amount: Double, val date: String)
