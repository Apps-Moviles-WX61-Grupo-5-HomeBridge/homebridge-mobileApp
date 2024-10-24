package com.example.app_salesquare_homebridge.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.app_salesquare_homebridge.models.Location
import com.example.app_salesquare_homebridge.models.Publication

@Database(entities = [Location::class, Publication::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): PublicationsDao

    companion object{
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room
                    .databaseBuilder(context, AppDatabase::class.java, "homebridge.db")
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }

}