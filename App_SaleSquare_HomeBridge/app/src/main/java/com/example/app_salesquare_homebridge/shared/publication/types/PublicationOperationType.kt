package     com.example.app_salesquare_homebridge.shared.publication.types



public final enum class PublicationOperationType
{
    Buy,
    Rent;

    companion object {
        public fun fromInt(value: Int): PublicationOperationType {
            return when (value) {
                0 -> Buy
                1 -> Rent

                else -> throw IllegalArgumentException()
            }
        }
        public fun stringFromType(type: PublicationOperationType): String {
            return when (type) {
                Buy -> "Compra"
                Rent -> "Venta"
            }
        }
    }
}