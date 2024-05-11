package com.example.seniorsprojectui
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.seniorsprojectui.databinding.ActivitySplashAccountBinding

class SplashAccountActivity : AppCompatActivity() {
        private lateinit var binding: ActivitySplashAccountBinding
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivitySplashAccountBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.btnSetUpAccount.setOnClickListener {
                startActivity(Intent(this, AddNewAccountActivity::class.java))
            }
        }
    }