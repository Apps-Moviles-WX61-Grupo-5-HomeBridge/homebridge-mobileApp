package     com.example.app_salesquare_homebridge.shared.publication

import      com.example.app_salesquare_homebridge.shared.publication.types.PublicationOperationType
import      com.example.app_salesquare_homebridge.shared.publication.types.PublicationPlaceType



public final class SearchFilterWrapper {
    companion object {
        public var location: String = ""
        public var operationType: PublicationOperationType = PublicationOperationType.NotDefined
        public var placeType: PublicationPlaceType = PublicationPlaceType.NotDefined
        public var priceFrom: Float = -1f
        public var priceTo: Float = -1f
        public var rooms: Int = -1
        public var bathrooms: Int = -1
        public var garages: Int = -1
        public var areaFrom: Float = -1f
        public var areaTo: Float = -1f

        public fun allInvalid(): Boolean {
            return this.location.isEmpty() && this.operationType == PublicationOperationType.NotDefined && this.placeType == PublicationPlaceType.NotDefined && this.priceFrom == -1f && this.priceTo == -1f && this.rooms == -1 && this.bathrooms == -1 && this.garages == -1 && this.areaFrom == -1f && this.areaTo == -1f
        }
    }
}