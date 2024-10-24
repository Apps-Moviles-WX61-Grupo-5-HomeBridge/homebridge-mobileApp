package     com.example.app_salesquare_homebridge.network

import      com.example.app_salesquare_homebridge.communication.PublicationResponse
import      retrofit2.Call
import      retrofit2.http.GET
import      retrofit2.http.Header
import      retrofit2.http.Query



interface PostApiService {
    @GET("api/v1/publication/publications")
    fun publications(
        @Header("Authorization") token: String,
        @Query("amount") amount: Int
    ): Call<List<PublicationResponse>>
}