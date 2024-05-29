package com.example.seniorsprojectui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.adapters.MyWalletsAdapter
import com.example.seniorsprojectui.backend.FilterDataModel
import com.example.seniorsprojectui.databinding.ActivityMyWalletsBinding

class MyWalletsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMyWalletsBinding
    private lateinit var rv : RecyclerView
    private lateinit var adapter : MyWalletsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMyWalletsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rv = binding.rvMyWallets
        adapter = MyWalletsAdapter(FilterDataModel.myWalletsList)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)


        binding.ivBackArrow.setOnClickListener {
            finish()
        }

        binding.btnAddNewWalletFromMyWallets.setOnClickListener {
            startActivity(Intent(this, AddNewWalletActivity::class.java))
        }

    }
}