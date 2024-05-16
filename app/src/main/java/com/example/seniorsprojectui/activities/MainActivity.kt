package com.example.seniorsprojectui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.seniorsprojectui.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


//        val db = Room.databaseBuilder(
//            applicationContext,
//            TransactionDatabase::class.java, "database-name"
//        ).build()
//
//        GlobalScope.launch {
//
////            db.transacDao().insertItem(TransactionTable(1,"Mudassar", "Irshad"))
////            db.transacDao().deleteTransactionsTable()
//        }

//        CoroutineScope(IO).launch {
//            val transactions = db.transacDao().getAllTransactions()
//            for (transaction in transactions) {
//                Log.d("MaraDLAo", "Transaction: ID - ${transaction.uid}, Name - ${transaction.firstName}, Description - ${transaction.lastName}")
//            }
//        }

    }
}