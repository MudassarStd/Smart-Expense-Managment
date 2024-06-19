package com.example.seniorsprojectui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.adapters.TransactionRVAdapter
import com.example.seniorsprojectui.backend.CurrentUserSession
import com.example.seniorsprojectui.backend.Transaction
import com.example.seniorsprojectui.backend.Wallet
import com.example.seniorsprojectui.databinding.ActivityWalletDetailsBinding
import com.example.seniorsprojectui.dbvm.ViewModelTransaction

class WalletDetailsActivity : AppCompatActivity() , TransactionRVAdapter.onItemClickListener{

    private lateinit var binding : ActivityWalletDetailsBinding
    private lateinit var wallet: Wallet
    private lateinit var viewModelTransaction: ViewModelTransaction
    private lateinit var rawList : List<Transaction>
    private lateinit var adapter : TransactionRVAdapter
    private val TAG = "tesstingdutoanfd"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWalletDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModelTransaction = ViewModelProvider(this)[ViewModelTransaction::class.java]


        adapter = TransactionRVAdapter(emptyList())
        adapter.setOnItemClickListener(this)
        binding.rvWalletDetails.adapter = adapter
        binding.rvWalletDetails.layoutManager = LinearLayoutManager(this)

        wallet = getDataFromIntent()

        binding.tvWalletName.setText(wallet.walletName)
        binding.tvWalletAmount.setText(wallet.walletAmount)

        viewModelTransaction.fetchCurrentUserTransactions(CurrentUserSession.currentUserId)
        viewModelTransaction.transactions.observe(this){
            rawList = it
            getWalletTransactions(wallet.walletName, rawList)
        }


    }
    private fun getWalletTransactions(walletName: String, rawList: List<Transaction>)  {
        val list = rawList.filter { it.wallet == walletName }
        adapter.updateTransactionData(list)
    }

    private fun getDataFromIntent() : Wallet {
    val wallet =  intent.getSerializableExtra("wallet") as? Wallet
        return wallet!!
    }

    override fun onItemClick(itemPosition: Int) {
        TODO("Not yet implemented")
    }

    override fun onItemLongClick(itemPosition: Int) {
        TODO("Not yet implemented")
    }
}