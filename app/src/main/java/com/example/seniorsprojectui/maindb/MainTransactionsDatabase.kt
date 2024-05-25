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
//
//@Database (entities = [Transaction::class, UserData::class], version = 2)
//abstract class MainTransactionsDatabase : RoomDatabase() {
//    abstract fun transacactionDaoConnector() : MainTransactionsDao
//    abstract fun userDao() : UserDao
////    abstract fun budgetCategoryDao() : BudgetCategoryDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: MainTransactionsDatabase? = null
//
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
//
//
//        // creation of singleton object of database class
//        fun getInstance(context: Context): MainTransactionsDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    MainTransactionsDatabase::class.java,
//                    "Main_Transaction_db"
//                )
//                    .addMigrations(MIGRATION_1_2)
//                    .build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
//}