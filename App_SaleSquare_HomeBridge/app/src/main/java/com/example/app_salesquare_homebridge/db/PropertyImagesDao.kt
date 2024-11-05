package com.example.app_salesquare_homebridge.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.app_salesquare_homebridge.models.PropertyImages

@Dao
interface PropertyImagesDao {
    @Insert
    fun insertOne(propertyImages: PropertyImages)

    @Query("SELECT * FROM propertyImages")
    fun getAll(): List<PropertyImages>

    @Delete
    fun delete(propertyImages: PropertyImages)
}