package com.example.seniorsprojectui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.seniorsprojectui.activities.HomeActivity
import com.example.seniorsprojectui.databinding.ActivitySplashOkBinding

class SplashOkActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashOkBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashOkBinding.inflate(layoutInflater)
        setContentView(binding.root)

    binding.btnNextToHome.setOnClickListener {
        startActivity(Intent(this, HomeActivity::class.java))

    }
    }
}