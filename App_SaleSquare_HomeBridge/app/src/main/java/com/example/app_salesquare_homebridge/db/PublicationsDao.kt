package com.example.app_salesquare_homebridge.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.app_salesquare_homebridge.models.Publications

@Dao
interface PublicationsDao {
    @Insert
    fun insertOne(publications: Publications)

    @Query("SELECT * FROM publications")
    fun getAll(): List<Publications>

    @Delete
    fun delete(publications: Publications)
}