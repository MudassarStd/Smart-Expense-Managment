package com.example.seniorsprojectui.maindb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.seniorsprojectui.backend.Transaction

@Dao
interface MainTransactionsDao {
    @Insert
    suspend  fun insertItem(transacItem: Transaction)
    @Query("SELECT * FROM `Transaction`")
    suspend fun getAllTransactions(): List<Transaction>
    @Query("SELECT * FROM `Transaction` where uid = :id")
    suspend fun getCurrentUserTransaction(id : Int): List<Transaction>
    @Delete
    suspend fun deleteItem(transacItem: Transaction)

    @Query("Delete from `Transaction` where Tid = :tid")
    suspend fun deleteById(tid : Int)
    @Update
    suspend fun updateItem(transacItem: Transaction)
    @Query("DELETE FROM `Transaction` where uid = :uid")
    suspend fun deleteAllTransactions(uid : Int)
}