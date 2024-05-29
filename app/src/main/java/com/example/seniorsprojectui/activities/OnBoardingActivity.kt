package com.example.seniorsprojectui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.seniorsprojectui.databinding.ActivityOnboardingBinding


class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSignUPOnBoarding.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }
        
        binding.btnLoginOnBoarding.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}