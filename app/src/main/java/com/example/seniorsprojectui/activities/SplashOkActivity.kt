package com.example.seniorsprojectui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.seniorsprojectui.databinding.ActivitySplashOkBinding
import com.example.seniorsprojectui.dbvm.ViewModelTransaction

class SplashOkActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashOkBinding
    private lateinit var viewModelTransaction: ViewModelTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashOkBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModelTransaction = ViewModelProvider(this)[ViewModelTransaction::class.java]
//        viewModelTransaction.fetchCurrentUserTransactions(TransactionDataModel.currentUserId)
//        Toast.makeText(this,"${viewModelTransaction.transactionsList}, ${TransactionDataModel.currentUserId}", Toast.LENGTH_SHORT).show()


    binding.btnNextToHome.setOnClickListener {
        startActivity(Intent(this, HomeActivity::class.java))
    }
    }
}