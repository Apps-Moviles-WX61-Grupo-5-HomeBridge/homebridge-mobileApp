package com.example.app_salesquare_homebridge.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Publication (
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

    @ColumnInfo(name = "createdDate")
    var createdDate: String?,

    @ColumnInfo(name = "imagesList")
    var imagesList: List<String>?,

    @ColumnInfo(name = "coveredArea")
    var coveredArea: Float?,

    @ColumnInfo(name = "totalArea")
    var totalArea: Float?,

    @ColumnInfo(name = "type")
    var type: Int?,

    @ColumnInfo(name = "operation")
    var operation: Int?,

    @ColumnInfo(name = "delivery")
    var delivery: String?,

    @ColumnInfo(name = "dormitories")
    var dormitory: Int?,

    @ColumnInfo(name = "bathrooms")
    var bathroom: Int?,

    @ColumnInfo(name = "parkingLot")
    var parkingLot: Int?,

    @ColumnInfo(name = "saleState")
    var saleState: String?,

    @ColumnInfo(name = "projectStage")
    var projectStage: String?,

    @ColumnInfo(name = "projectStartDate")
    var projectStartDate: String?,

    @ColumnInfo(name = "antiquity")
    var antiquity: Int?,

    @ColumnInfo(name = "size")
    var size: Int?,

    @ColumnInfo(name = "garages")
    var garages: Int?,

    @ColumnInfo(name = "rooms")
    var rooms: Int?
)