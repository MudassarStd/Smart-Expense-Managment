package com.example.seniorsprojectui.maindb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.seniorsprojectui.backend.BudgetCategory
import com.example.seniorsprojectui.backend.Transaction

@Dao
interface MainTransactionsDao {
    @Insert
    suspend  fun insertItem(transacItem: Transaction)
    @Query("SELECT * FROM `Transaction`")
    suspend fun getAllTransactions(): List<Transaction>
    @Delete
    suspend fun deleteItem(transacItem: Transaction)
    @Update
    suspend fun updateItem(transacItem: Transaction)
    @Query("DELETE FROM `Transaction`")
    suspend fun deleteAllTransactions()

    // methods for budgetCategory

//    @Insert
//    suspend  fun insertBudget(budgetItem: BudgetCategory)
//    @Delete
//    suspend  fun deleteBudget(budgetItem: BudgetCategory)
//    @Query("Select * From BudgetCategory")
//    suspend fun getAllBudgets() : List<BudgetCategory>
}