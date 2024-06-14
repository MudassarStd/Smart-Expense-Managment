package com.example.seniorsprojectui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.seniorsprojectui.databinding.ActivityAddNewAccountBinding

class AddNewAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddNewAccountContinue.setOnClickListener {
            startActivity(Intent(this, AddNewWalletActivity::class.java))
        }
    }
}