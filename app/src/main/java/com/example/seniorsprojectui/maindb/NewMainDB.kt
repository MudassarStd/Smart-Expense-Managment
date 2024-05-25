package com.example.seniorsprojectui.maindb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.seniorsprojectui.backend.BudgetCategory
import com.example.seniorsprojectui.backend.Transaction
import com.example.seniorsprojectui.backend.UserData

@Database (entities = [Transaction::class, UserData::class, BudgetCategory::class], version = 1)
abstract class NewMainDB : RoomDatabase() {
    abstract fun transactionsDao() : MainTransactionsDao
    abstract fun userDao() : UserDao
    abstract fun budgetCategoryDao() : BudgetCategoryDao

    companion object {
        @Volatile
        private var INSTANCE: NewMainDB? = null

//        val MIGRATION_1_2 = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL(
//                    "CREATE TABLE IF NOT EXISTS `userData` (`userid` INTEGER PRIMARY KEY NOT NULL, `username` TEXT NOT NULL, `userEmail` TEXT NOT NULL, `userpassword` TEXT NOT NULL)"
//                )
////                database.execSQL(
////                    "CREATE TABLE IF NOT EXISTS `BudgetCategory` (`Budgetid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `category` TEXT NOT NULL, `totalAmount` TEXT NOT NULL, 'month' TEXT NOT NULL)"
////                )
//            }
//        }


        // creation of singleton object of database class
        fun getInstance(context: Context): NewMainDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewMainDB::class.java,
                    "Main_App_Database"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}