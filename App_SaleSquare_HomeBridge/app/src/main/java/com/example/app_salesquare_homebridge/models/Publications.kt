package com.example.app_salesquare_homebridge.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Publications (
    @PrimaryKey
    var id: Int? = null,

    @ColumnInfo(name = "userId")
    var userId: Int?,

    @ColumnInfo(name ="title")
    var title: String?,

    @ColumnInfo(name = "description")
    var description: String?,

    @ColumnInfo(name = "price")
    var price: Double?,

    @ColumnInfo(name = "location")
    var location: String?,

    @ColumnInfo(name = "address")
    var priority: Int,

    @ColumnInfo(name = "hasExpired")
    var hasExpired: Boolean?,

   @ColumnInfo(name = "createdDate")
    var createdDate: String?,

    @ColumnInfo(name = "updatedDate")
    var updatedDate: String?,

    @ColumnInfo(name = "isDeleted")
    var isDeleted: Boolean?,

    @ColumnInfo(name = "images")
    var images: List<String>?
)