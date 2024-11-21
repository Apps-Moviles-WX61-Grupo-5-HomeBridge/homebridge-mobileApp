package     com.example.app_salesquare_homebridge.network.publications

import      com.example.app_salesquare_homebridge.shared.publication.types.PublicationOperationType
import      com.example.app_salesquare_homebridge.shared.publication.types.PublicationPlaceType



public final data class GetPublicationsRequest(
    public val location: String = "",
    public val operationType: Int = PublicationOperationType.Buy.ordinal,
    public val placeType: Int = PublicationPlaceType.House.ordinal,
    public val priceFrom: Float = 0f,
    public val priceTo: Float = 0f,
    public val rooms: Int = 0,
    public val bathrooms: Int = 0,
    public val garages: Int = 0,
    public val areaFrom: Float = 0f,
    public val areaTo: Float = 0f,
    public val amount: Int = 0
)