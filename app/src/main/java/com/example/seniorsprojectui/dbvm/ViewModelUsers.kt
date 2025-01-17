package com.example.seniorsprojectui.dbvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.seniorsprojectui.backend.CurrentUserSession
import com.example.seniorsprojectui.backend.Transaction
import com.example.seniorsprojectui.backend.TransactionDataModel
import com.example.seniorsprojectui.backend.UserData
import com.example.seniorsprojectui.maindb.MainDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelUsers(application: Application) : AndroidViewModel(application) {


    private val db : MainDatabase = MainDatabase.getInstance(application)

    // users List
    var registeredUsers : List<UserData> = listOf()
    // Logged In user
    var currentUserId : Int = -1
    var currentUserName : String = ""
    // user transactions
    var currentUserTransactionsList : List<Transaction> = listOf()

    init {
        fetchUsers()
    }
     suspend fun isTaken(name: String, email: String): Boolean {
        return db.userDao().isTaken(name, email)
    }

    suspend fun allowLogin(name : String, password : String) : Boolean {
        return db.userDao().allowLogin(name, password)
    }

    fun insertUser(user : UserData)
    {
        viewModelScope.launch (Dispatchers.IO){
            db.userDao().insertUser(user)
            fetchUsers()
        }
    }
    fun updateUser(user : UserData)
    {
        viewModelScope.launch (Dispatchers.IO){
            db.userDao().updateUser(user)
            getCurrentUserInfo(user.uid)
        }
    }

    private fun fetchUsers()  {
        viewModelScope.launch(Dispatchers.IO) {
            registeredUsers = db.userDao().getAllUsers()
        }
    }

    fun delUsers()
    {
        viewModelScope.launch(Dispatchers.IO) {
            db.userDao().deleteAllUsers()
            fetchUsers()
        }
    }

    suspend fun getCurrentUserId(email: String) : Int{
        val id = db.userDao().getCurrentUserId(email)
//        fetchCurrentUserTransactions(id)
        getCurrentUserInfo(id)
        return id
    }

    private fun getCurrentUserInfo(uid : Int)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val userInfo = db.userDao().getCurrentUserInfo(uid)
            CurrentUserSession.currentUserName = userInfo.username
            CurrentUserSession.currentUserData = userInfo
        }
    }


//     fun fetchCurrentUserTransactions(uid : Int)
//    {
//        viewModelScope.launch(Dispatchers.IO) {
//            currentUserTransactionsList = db.transactionsDao().getCurrentUserTransaction(uid)
////            updateAllStakeHolders()
//        }
//    }


}