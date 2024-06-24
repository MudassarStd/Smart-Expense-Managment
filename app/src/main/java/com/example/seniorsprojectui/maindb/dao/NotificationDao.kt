package com.example.seniorsprojectui.maindb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.seniorsprojectui.backend.NotificationInstance

@Dao
interface NotificationDao {
    @Insert
    suspend  fun insertNotification(notification: NotificationInstance)
    @Query("SELECT * FROM `NotificationInstance` where userid = :id")
    suspend fun getCurrentUserNotifications(id : Int): List<NotificationInstance>
    @Query("DELETE FROM `NotificationInstance` where userid = :uid")
    suspend fun deleteNotification(uid : Int)
}