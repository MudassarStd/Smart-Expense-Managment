package com.example.seniorsprojectui.maindb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.seniorsprojectui.backend.Transaction
import com.example.seniorsprojectui.backend.Wallet

@Dao
interface WalletDao {

    @Insert
    suspend  fun insertWallet(wallet : Wallet)

    @Query("SELECT * FROM `Wallet` where userid = :id")
    suspend fun getCurrentUserWallet(id : Int): List<Wallet>
    @Delete
    suspend fun deleteWallet(wallet : Wallet)
    @Update
    suspend fun updateWallet(wallet : Wallet)
    @Query("DELETE FROM `Wallet` where userid = :uid")
    suspend fun deleteAllWallets(uid : Int)
    @Query("Select * FROM `Wallet`")
    suspend fun getAllWallets() : List<Wallet>
}