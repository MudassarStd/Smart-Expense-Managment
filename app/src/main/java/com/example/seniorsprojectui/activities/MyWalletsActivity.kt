package com.example.seniorsprojectui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
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
import com.example.seniorsprojectui.dbvm.WalletsViewModel

class MyWalletsActivity : AppCompatActivity() , MyWalletsAdapter.OnWalletClickInterface{
    private lateinit var binding : ActivityMyWalletsBinding
    private lateinit var rv : RecyclerView
    private lateinit var adapter : MyWalletsAdapter
    private lateinit var walletViewModel : WalletsViewModel


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

        walletViewModel = ViewModelProvider(this)[WalletsViewModel::class.java]
        walletViewModel.fetchCurrentUserWallets(CurrentUserSession.currentUserId)



        rv = binding.rvMyWallets
        adapter = MyWalletsAdapter(emptyList())
        adapter.setOnwalletClickListener(this)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)


        walletViewModel.wallets.observe(this){
            adapter.updateMyWalletsAdapter(it)
        }

        walletViewModel.walletsAmountSum.observe(this)
        {
            binding.tvWalletAmountSum.text = "Rs." + it.toString()
        }



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

    override fun onResume() {
        super.onResume()
        walletViewModel.fetchCurrentUserWallets(CurrentUserSession.currentUserId)
    }
}