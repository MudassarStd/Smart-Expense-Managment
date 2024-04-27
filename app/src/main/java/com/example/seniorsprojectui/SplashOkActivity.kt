package com.example.seniorsprojectui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.seniorsprojectui.databinding.ActivitySplashOkBinding

class SplashOkActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashOkBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashOkBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}