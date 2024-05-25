package com.example.seniorsprojectui.dbvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.seniorsprojectui.backend.TransactionDataModel
import com.example.seniorsprojectui.backend.UserData
import com.example.seniorsprojectui.maindb.NewMainDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelUsers(application: Application) : AndroidViewModel(application) {


    private val db : NewMainDB = NewMainDB.getInstance(application)

    // users List
    var registeredUsers : List<UserData> = listOf()
    // Logged In user
    var currentUserId : Int = -1
    var currentUserName : String = ""


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
        getCurrentUserInfo(id)
        return id
    }

    private fun getCurrentUserInfo(uid : Int)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val userInfo = db.userDao().getCurrentUserInfo(uid)
            TransactionDataModel.currentUserName = userInfo.username
        }
    }


}