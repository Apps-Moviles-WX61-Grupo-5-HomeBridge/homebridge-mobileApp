package com.example.app_salesquare_homebridge.communication

import com.example.app_salesquare_homebridge.models.Publications
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PublicationResponse (
    private var id: Int,
    private var userId: Int,
    private var title: String,
    private var description: String,
    private var price: Double,
    private var _Location: Location,
    private var priority: Int,
    private var hasExpired: Boolean,
    private var createdDate: String,
    private var updatedDate: String,
    private var isDeleted: Boolean,
    private var images: List<String>
) {
    fun toPublication() : Publications {
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val date: Date = inputDateFormat.parse(createdDate)

        val formattedDate: String = outputDateFormat.format(date)

        return Publications(
            id = id,
            userId = userId,
            title = title,
            description = description,
            price = price,
            priority = priority,
            hasExpired = hasExpired,
            createdDate = formattedDate,
            updatedDate = updatedDate,
            isDeleted = isDeleted,
            location = _Location.address,
            images = images
        )
    }
}

data class Location (
    var address: String
)