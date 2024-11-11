package     com.example.app_salesquare_homebridge.shared.publication.types



public final enum class PublicationAntiqueType
{
    NewlyBuilt,
    Old,
    UnderConstruction;

    companion object {
        public fun fromInt(value: Int): PublicationAntiqueType {
            return when (value) {
                0 -> NewlyBuilt
                1 -> Old
                2 -> UnderConstruction

                else -> throw IllegalArgumentException()
            }
        }
        public fun stringFromType(type: PublicationAntiqueType): String {
            return when (type) {
                NewlyBuilt -> "A estrenar"
                Old -> "Años de antiguedad"
                UnderConstruction -> "En construcción"
            }
        }
    }
}