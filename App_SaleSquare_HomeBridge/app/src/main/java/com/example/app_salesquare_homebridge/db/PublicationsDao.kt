package com.example.app_salesquare_homebridge.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.app_salesquare_homebridge.models.Publication

@Dao
interface PublicationsDao {
    @Insert
    fun insertOne(publications: Publication)

    @Query("SELECT * FROM publication")
    fun getAll(): List<Publication>

    @Delete
    fun delete(publications: Publication)


    @Query("DELETE FROM publication WHERE id = :postId")
    fun deleteById(postId: Int)
}