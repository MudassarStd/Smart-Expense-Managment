package com.example.seniorsprojectui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.databinding.ActivityEditTransactionBinding

class EditTransactionActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditTransactionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTransactionBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // recieving data from other activity
        val time = intent.getStringExtra("time")
        val date = intent.getStringExtra("date")
        val amount = intent.getStringExtra("amount")
        val category = intent.getStringExtra("category")
        val wallet = intent.getStringExtra("wallet")
        val description = intent.getStringExtra("description")
        val attachmentStatus = intent.getStringExtra("attachmentStatus")
        val transactionType = intent.getStringExtra("transactionType")

        binding.tvEditAmount.text = "Rs."+amount
        binding.tvEditCategory.text = category
        binding.tvEditDate.text = date
        binding.tvEditDescription.text = description
        binding.tvEditWallet.text = wallet
        binding.tvEditTime.text = time

    }
}