package com.example.app_salesquare_homebridge.communication

import com.example.app_salesquare_homebridge.models.Publications
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PublicationResponse (
    private var id: Int,
    private var title: String,
    private var description: String,
    private var price: Double,
    private var _Location_Address: String,
    private var userId: Int,
    private var imagesList: List<String>,
    private var coveredArea: Float,
    private var totalArea: Float,
    private var type: String,
    private var operation: String,
    private var delivery: String,
    private var dormitoryQuantity: Int,
    private var bathroomQuantity: Int,
    private var parkingLotQuantity: Int,
    private var saleState: String,
    private var projectStage: String,
    private var projectStartDate: String,
    private var createdDate: String,
    private var antiquity: Int
) {
    fun toPublication() : Publications {
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val date: Date = inputDateFormat.parse(createdDate)
        val projectStartDate: Date = inputDateFormat.parse(projectStartDate)

        val formattedCreatedDate: String = outputDateFormat.format(date)
        val formattedProjectStartDate: String = outputDateFormat.format(projectStartDate)

        return Publications(
            id = id,
            title = title,
            description = description,
            price = price,
            createdDate = formattedCreatedDate,
            location = _Location_Address,
            userId = userId,
            imagesList = imagesList,
            coveredArea = coveredArea,
            totalArea = totalArea,
            type = type,
            operation = operation,
            delivery = delivery,
            dormitory = dormitoryQuantity,
            bathroom = bathroomQuantity,
            parkingLot = parkingLotQuantity,
            saleState = saleState,
            projectStage = projectStage,
            projectStartDate = formattedProjectStartDate,
            antiquity = antiquity
        )
    }
}