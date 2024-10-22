package     com.example.app_salesquare_homebridge.shared.user

import      android.os.Parcelable
import      kotlinx.parcelize.Parcelize



@Parcelize
public final data class UserWrapper(
    private var m_UserId: Int = 0,
    private var m_Token: String = ""
): Parcelable {
    public fun userId(id: Int): Unit {
        this.m_UserId = id
    }
    public fun userId(): Int = this.m_UserId

    public fun token(token: String): Unit {
        this.m_Token = token
    }
    public fun token(): String = this.m_Token
}