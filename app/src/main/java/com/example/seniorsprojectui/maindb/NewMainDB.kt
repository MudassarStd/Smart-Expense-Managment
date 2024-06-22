//package com.example.seniorsprojectui.maindb
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import androidx.room.migration.Migration
//import androidx.sqlite.db.SupportSQLiteDatabase
//import com.example.seniorsprojectui.backend.BudgetCategory
//import com.example.seniorsprojectui.backend.Transaction
//import com.example.seniorsprojectui.backend.UserData
//import com.example.seniorsprojectui.backend.Wallet
//
//@Database(entities = [Transaction::class, UserData::class, BudgetCategory::class, Wallet::class], version = 1)
//abstract class NewMainDB : RoomDatabase() {
//    abstract fun transactionsDao(): MainTransactionsDao
//    abstract fun userDao(): UserDao
//    abstract fun budgetCategoryDao(): BudgetCategoryDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: NewMainDB? = null
//
//        fun getInstance(context: Context): NewMainDB {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    NewMainDB::class.java,
//                    "Main_App_Database"
//                )
//
//                    .build()
//                INSTANCE = instance
//                instance
//            }
//        }
//
////        val MIGRATION_1_2 = object : Migration(1, 2) {
////            override fun migrate(database: SupportSQLiteDatabase) {
////                // Adding new columns to Transaction and UserData tables
////                database.execSQL("ALTER TABLE Transaction ADD COLUMN attachmentType TEXT DEFAULT ''")
////                database.execSQL("ALTER TABLE UserData ADD COLUMN userImage TEXT DEFAULT ''")
////
////                // Creating the new Wallet table
////                database.execSQL(
////                    "CREATE TABLE IF NOT EXISTS Wallet (" +
////                            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
////                            "userid INTEGER NOT NULL, " +
////                            "walletName TEXT NOT NULL, " +
////                            "walletAmount TEXT NOT NULL)"
////                )
//            }
//
//
//}
