package     com.example.app_salesquare_homebridge.network

import com.example.app_salesquare_homebridge.communication.PropertyImagesResponse
import      com.example.app_salesquare_homebridge.communication.PublicationResponse
import com.example.app_salesquare_homebridge.models.PropertyImages
import com.example.app_salesquare_homebridge.models.publications.Publication
import com.example.app_salesquare_homebridge.network.publications.GetPublicationsRequest
import      retrofit2.Call
import retrofit2.http.Body
import      retrofit2.http.GET
import retrofit2.http.HTTP
import      retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import      retrofit2.http.Query



interface PostApiService {
    @GET("api/v1/publication/publications")
    fun publications(
        @Header("Authorization") token: String,
        @Query("Location") location: String,
        @Query("OperationType") operationType: Int,
        @Query("PlaceType") placeType: Int,
        @Query("PriceFrom") priceFrom: Float,
        @Query("PriceTo") priceTo: Float,
        @Query("Rooms") rooms: Int,
        @Query("Bathrooms") bathrooms: Int,
        @Query("Garages") garages: Int,
        @Query("AreaFrom") areaFrom: Float,
        @Query("AreaTo") areaTo: Float
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
        @Query("publicationId") amount: Int
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

    @PUT("api/v1/publication/updateImageList")
    fun updateUrls(
        @Header("Authorization") token: String,
        @Body urlList: PropertyImages
    ): Call<Void>

    @HTTP(method = "DELETE", path = "api/v1/publication/deletePublication", hasBody = true)
    fun deletePost(
        @Header("Authorization") token: String,
        @Body postId: Int
    ): Call<Void>
}