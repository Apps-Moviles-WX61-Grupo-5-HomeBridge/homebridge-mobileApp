package com.example.app_salesquare_homebridge.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User (
    @PrimaryKey
    val id: Int? = null,

    @ColumnInfo(name = "username")
    val username: String?,

    @ColumnInfo(name = "password")
    val password: String?,

    @ColumnInfo(name = "email")
    val email: String?,

    @ColumnInfo(name = "phoneNumber")
    val phone: String?,
)