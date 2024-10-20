package com.example.app_salesquare_homebridge.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.app_salesquare_homebridge.models.Location

@Database(entities = [Location::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

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