package com.example.seniorsprojectui.maindb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Query
import com.example.seniorsprojectui.backend.UserData

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(userData : UserData)
    @Update
    suspend fun updateUser(userData : UserData)

    @Query("SELECT EXISTS (SELECT 1 FROM UserData WHERE username = :name OR useremail = :email)")
    suspend fun isTaken(name: String, email: String): Boolean

    @Query("SELECT EXISTS (SELECT 1 FROM UserData WHERE  useremail = :name AND userpassword = :password)")
    suspend fun allowLogin(name: String, password: String): Boolean

    @Query("Select * from UserData")
    suspend fun getAllUsers() : List<UserData>

    @Query("Delete from UserData")
    suspend fun deleteAllUsers()
    @Query("Select uid from UserData where useremail = :email")
    suspend fun getCurrentUserId(email : String) : Int

    @Query("Select * from UserData where uid = :id")
    suspend fun getCurrentUserInfo(id : Int) : UserData
}