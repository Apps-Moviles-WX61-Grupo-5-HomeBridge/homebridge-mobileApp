package     com.example.app_salesquare_homebridge.network

import      com.example.app_salesquare_homebridge.communication.PublicationResponse
import      retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import      retrofit2.http.GET
import retrofit2.http.HTTP
import      retrofit2.http.Header
import retrofit2.http.Path
import      retrofit2.http.Query



interface PostApiService {
    @GET("api/v1/publication/publications")
    fun publications(
        @Header("Authorization") token: String,
        @Query("amount") amount: Int
    ): Call<List<PublicationResponse>>

    /*@DELETE("api/v1/publication/{postId}")
    fun deletePost(
        @Header("Authorization") token: String,
        @Path("postId") postId: Int
    ): Call<Void>*/

    @HTTP(method = "DELETE", path = "api/v1/publication/deletePublication", hasBody = true)
    fun deletePost(
        @Header("Authorization") token: String,
        @Body postId: Int
    ): Call<Void>
}