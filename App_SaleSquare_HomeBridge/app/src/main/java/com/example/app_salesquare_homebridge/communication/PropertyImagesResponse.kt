package com.example.app_salesquare_homebridge.communication

import com.example.app_salesquare_homebridge.models.PropertyImages

class PropertyImagesResponse (
    private var publicationId: Int,
    private var imageList: List<String>
){
    fun toPropertyImages() : PropertyImages {
        return PropertyImages(
            publicationId = publicationId,
            imageList = imageList
        )
    }
}