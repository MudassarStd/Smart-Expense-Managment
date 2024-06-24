package com.example.seniorsprojectui.dbvm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.seniorsprojectui.backend.BudgetCategory
import com.example.seniorsprojectui.backend.CurrentUserSession
import com.example.seniorsprojectui.backend.Transaction
import com.example.seniorsprojectui.backend.Wallet
import com.example.seniorsprojectui.maindb.MainDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WalletsViewModel(application: Application) : AndroidViewModel(application) {

    private val db: MainDatabase = MainDatabase.getInstance(application)

    private val walletDao = db.walletDao()
    private val _wallets = MutableLiveData<List<Wallet>>()
    val wallets: LiveData<List<Wallet>> get() = _wallets


    private val _walletsAmountSum = MutableLiveData<Long>()
    val walletsAmountSum: LiveData<Long> get() = _walletsAmountSum

    fun fetchCurrentUserWallets(uid : Int)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val list  = walletDao.getCurrentUserWallet(uid)
            _wallets.postValue(list)

            val amountSum = list.sumOf { it.walletAmount.toDouble() ?: 0.0}
            _walletsAmountSum.postValue(amountSum.toLong())
        }
    }


    fun insertWallet(wallet: Wallet) {
        viewModelScope.launch(Dispatchers.IO) {
            walletDao.insertWallet(wallet)
           fetchCurrentUserWallets(wallet.userid)
        }
    }



    fun deleteAllWallets(uid : Int) {
        viewModelScope.launch(Dispatchers.IO) {
           walletDao.deleteAllWallets(uid)
        }
    }

    fun getAllWallets() {
        viewModelScope.launch(Dispatchers.IO) {
           Log.d("fhskdjfhs","Wallets in Database: ${walletDao.getAllWallets()}")
        }
    }

    fun deleteWallet(wallet: Wallet) {
        viewModelScope.launch(Dispatchers.IO) {
            walletDao.deleteWallet(wallet)
            fetchCurrentUserWallets(wallet.userid)
        }
    }

}