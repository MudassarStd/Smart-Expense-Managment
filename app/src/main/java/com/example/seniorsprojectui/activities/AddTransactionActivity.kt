package com.example.seniorsprojectui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.databinding.ActivityAddTransactionBinding
import com.example.seniorsprojectui.fragments.AddAttachmentBSV

class AddTransactionActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddTransactionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.llAddAttachment.setOnClickListener {
            AddAttachmentBSV().show(supportFragmentManager, AddAttachmentBSV().tag)
        }


    }
}