package     com.example.app_salesquare_homebridge.communication



public final data class UserRequest (
    val username: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
)