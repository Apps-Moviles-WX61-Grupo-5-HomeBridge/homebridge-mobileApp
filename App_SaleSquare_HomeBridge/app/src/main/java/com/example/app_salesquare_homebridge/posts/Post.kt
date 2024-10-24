package     com.example.app_salesquare_homebridge.posts



public final data class Post(
    public val price: Double = 0.0,
    public val location: String = "",
    public val size: Double = 0.0,
    public val rooms: Int = 0,
    public val bathrooms: Int = 0,
    public val garages: Int = 0,
    public val description: String = ""
)