package com.example.seniorsprojectui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.seniorsprojectui.databinding.ActivityAddNewAccountBinding

class AddNewAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}