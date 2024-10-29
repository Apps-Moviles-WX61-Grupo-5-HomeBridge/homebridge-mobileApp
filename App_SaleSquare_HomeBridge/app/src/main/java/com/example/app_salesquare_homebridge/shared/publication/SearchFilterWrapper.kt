package     com.example.app_salesquare_homebridge.shared.publication

import      com.example.app_salesquare_homebridge.shared.publication.types.PublicationOperationType
import      com.example.app_salesquare_homebridge.shared.publication.types.PublicationPlaceType



public final class SearchFilterWrapper {
    companion object {
        public var location: String = ""
        public var operationType: PublicationOperationType = PublicationOperationType.Buy
        public var placeType: PublicationPlaceType = PublicationPlaceType.House
        public var priceFrom: Float = 0f
        public var priceTo: Float = 0f
        public var rooms: Int = 0
        public var bathrooms: Int = 0
        public var garages: Int = 0
        public var areaFrom: Float = 0f
        public var areaTo: Float = 0f
    }
}