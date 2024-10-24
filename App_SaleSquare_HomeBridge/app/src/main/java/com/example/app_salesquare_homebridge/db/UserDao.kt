package com.example.app_salesquare_homebridge.db

import androidx.room.Dao
import androidx.room.Insert
import com.example.app_salesquare_homebridge.models.User

@Dao
interface UserDao {
    @Insert
    fun insertOne(user: User)
}