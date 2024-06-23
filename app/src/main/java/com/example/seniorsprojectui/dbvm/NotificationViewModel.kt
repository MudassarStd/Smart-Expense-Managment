package com.example.seniorsprojectui.dbvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.seniorsprojectui.backend.NotificationInstance
import com.example.seniorsprojectui.backend.Wallet
import com.example.seniorsprojectui.maindb.MainDatabase

class NotificationViewModel(application : Application) : AndroidViewModel(application) {
    private val db : MainDatabase = MainDatabase.getInstance(application)

    private val walletDao = db.walletDao()
    private val _notifications = MutableLiveData<List<NotificationInstance>>()

}