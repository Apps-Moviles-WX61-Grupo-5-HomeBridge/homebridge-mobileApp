package com.example.app_salesquare_homebridge.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Location (
    @PrimaryKey(autoGenerate = true)
    val id: Int?, //Required for Room

    @ColumnInfo
    var address: String?,

    @ColumnInfo
    var city: String?,

    @ColumnInfo
    var province: String?,

    @ColumnInfo
    var district: String?,

    @ColumnInfo
    var latitude: Double?,

    @ColumnInfo
    var longitude: Double?
)