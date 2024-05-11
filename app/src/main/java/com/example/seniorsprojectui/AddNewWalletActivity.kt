package com.example.seniorsprojectui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.seniorsprojectui.databinding.ActivityAddNewWalletBinding

class AddNewWalletActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewWalletBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewWalletBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddNewWalletContinue.setOnClickListener {
            startActivity(Intent(this, SplashOkActivity::class.java))
        }
    }
}