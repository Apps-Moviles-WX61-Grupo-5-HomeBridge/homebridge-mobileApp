package com.example.app_salesquare_homebridge.models

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class PropertyImages(
    @ColumnInfo(name = "publicationId")
    var publicationId: Int?,

    @ColumnInfo(name = "imagesList")
    var imageList: List<String>?
)