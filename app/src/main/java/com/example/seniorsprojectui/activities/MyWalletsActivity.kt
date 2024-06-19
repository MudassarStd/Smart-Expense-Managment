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
import com.example.seniorsprojectui.backend.CurrentUserSession
import com.example.seniorsprojectui.backend.FilterDataModel
import com.example.seniorsprojectui.backend.Transaction
import com.example.seniorsprojectui.backend.Wallet
import com.example.seniorsprojectui.databinding.ActivityMyWalletsBinding
import com.example.seniorsprojectui.dbvm.ViewModelTransaction

class MyWalletsActivity : AppCompatActivity() , MyWalletsAdapter.OnWalletClickInterface{
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
        adapter.setOnwalletClickListener(this)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)


        binding.ivBackArrow.setOnClickListener {
            finish()
        }

        binding.btnAddNewWalletFromMyWallets.setOnClickListener {
            startActivity(Intent(this, AddNewWalletActivity::class.java))
        }

    }

    override fun onItemClick(wallet : Wallet) {
        val intent = Intent(this, WalletDetailsActivity::class.java)
        intent.putExtra("wallet",wallet)

        startActivity(intent)
    }

    override fun onLongitemClick(itemPos: Int) {
        TODO("Not yet implemented")
    }
}