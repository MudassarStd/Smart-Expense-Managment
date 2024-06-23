package com.example.seniorsprojectui.backend

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Transaction(
    @PrimaryKey(autoGenerate = true) val Tid: Int,
    val time: String,
    val date: String,
    val month: String,
    val amount: String,
    val category: String,
    val wallet: String,
    val description: String,
    val attachment: String,
    val attachmentType: String, // added in migration 1-2
    val transactionType: String,
    val uid: Int
 ) : Serializable

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
    var username : String,
    val useremail : String,
    var userpassword : String,
    var userImage  : String // added
)

@Entity
data class Wallet(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val userid : Int,
    val walletName: String,
    val walletAmount : String
) : Serializable


@Entity
data class NotificationInstance(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val message: String,
    val timestamp: Long,
    val userId : Int
)

data class TransDummy(val id: Int, val type: String, val amount: Double, val date: String)
