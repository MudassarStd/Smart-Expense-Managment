package com.example.seniorsprojectui.maindb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.seniorsprojectui.backend.Transaction

@Database (entities = [Transaction::class], version = 1)
abstract class MainTransactionsDatabase : RoomDatabase() {
    abstract fun transacactionDaoConnector() : MainTransactionsDao

}