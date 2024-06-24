package com.example.seniorsprojectui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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

// Delay for 3 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
            finish() // Close the splash screen so the user can't go back to it
        }, 1500)
    }
}