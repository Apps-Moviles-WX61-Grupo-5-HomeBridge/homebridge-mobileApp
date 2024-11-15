package     com.example.app_salesquare_homebridge.network

import com.example.app_salesquare_homebridge.communication.PropertyImagesResponse
import      com.example.app_salesquare_homebridge.communication.PublicationResponse
import com.example.app_salesquare_homebridge.models.PropertyImages
import com.example.app_salesquare_homebridge.models.Publication
import      retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import      retrofit2.http.GET
import retrofit2.http.HTTP
import      retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import      retrofit2.http.Query



interface PostApiService {
    @GET("api/v1/publication/publications")
    fun publications(
        @Header("Authorization") token: String,
        @Query("amount") amount: Int
    ): Call<List<PublicationResponse>>

    @GET("api/v1/publication/justPublications")
    fun justPublications(
        @Header("Authorization") token: String
    ): Call<List<PublicationResponse>>

    /*@DELETE("api/v1/publication/{postId}")
    fun deletePost(
        @Header("Authorization") token: String,
        @Path("postId") postId: Int
    ): Call<Void>*/

    @GET("api/v1/publication/imageList")
    fun imageList(
        @Header("Authorization") token: String,
        @Query("amount") amount: Int
    ): Call<PropertyImagesResponse>

    @GET("api/v1/user/findById")
    fun findUserById(
        @Header("Authorization") token: String,
        @Query ("id") userId: Int
    ): Call<Map<String, Any>>

    @GET("api/v1/publication/userPublications")
    fun userPublications(
        @Header("Authorization") token: String,
        @Query("userId") userId: Int
    ): Call<List<Publication>>

    @POST("api/v1/publication/postImageList")
    fun sendUrls(
        @Header("Authorization") token: String,
        @Body urlList: PropertyImages
    ): Call<Void>

    @HTTP(method = "DELETE", path = "api/v1/publication/deletePublication", hasBody = true)
    fun deletePost(
        @Header("Authorization") token: String,
        @Body postId: Int
    ): Call<Void>
}