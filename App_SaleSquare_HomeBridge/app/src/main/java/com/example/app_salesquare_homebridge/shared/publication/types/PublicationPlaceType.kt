package     com.example.app_salesquare_homebridge.shared.publication.types



public final enum class PublicationPlaceType
{
    House,
    Apartment,
    Terrain;

    companion object {
        public fun fromInt(value: Int): PublicationPlaceType {
            return when (value) {
                0 -> House
                1 -> Apartment
                2 -> Terrain

                else -> throw IllegalArgumentException()
            }
        }
        public fun stringFromType(type: PublicationPlaceType): String {
            return when (type) {
                House -> "Casa"
                Apartment -> "Departamento"
                Terrain -> "Terreno"
            }
        }
    }
}