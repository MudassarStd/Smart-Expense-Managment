package com.example.seniorsprojectui.maindb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.seniorsprojectui.backend.BudgetCategory

@Dao
interface BudgetCategoryDao {
    @Insert
    suspend  fun insertBudget(budgetItem: BudgetCategory)
    @Delete
    suspend  fun deleteBudget(budgetItem: BudgetCategory)
    @Query("Select * From BudgetCategory")
     fun getAllBudgets() : List<BudgetCategory>
}