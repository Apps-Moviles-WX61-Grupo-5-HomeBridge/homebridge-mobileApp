package com.example.app_salesquare_homebridge.communication

import com.example.app_salesquare_homebridge.models.Publication
import java.text.SimpleDateFormat
import java.util.Locale

class PublicationResponse (
    private var id: Int,
    private var title: String,
    private var description: String,
    private var price: Double,
    private var _Location_Address: String,
    private var userId: Int,
    private var imageList: List<String>,
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
    private var antiquity: Int,
    private var size: Int,
    private var rooms: Int,
    private var garages: Int
) {
    fun toPublication() : Publication {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val createdDate = if (this.createdDate != null) {
            dateFormat.parse(this.createdDate)
        } else {
            null
        }

        return Publication(
            id = id,
            userId = userId,
            title = this.title ?: "",
            description = this.description ?: "",
            price = this.price ?: 0.0,
            createdDate = createdDate.toString(),
            location = this._Location_Address ?: "",
            coveredArea = this.coveredArea,
            totalArea = this.totalArea,
            type = this.type ?: "",
            operation = this.operation ?: "",
            delivery = this.delivery ?: "",
            dormitory = this.dormitoryQuantity ?: 0,
            bathroom = this.bathroomQuantity ?: 0,
            parkingLot = this.parkingLotQuantity ?: 0,
            saleState = this.saleState ?: "",
            projectStage = this.projectStage ?: "",
            projectStartDate = this.projectStartDate ?: "",
            antiquity = this.antiquity ?: 0,
            imagesList = this.imageList,
            size = this.size ?: 0,
            rooms = this.rooms ?: 0,
            garages = this.garages ?: 0
        )
    }
}