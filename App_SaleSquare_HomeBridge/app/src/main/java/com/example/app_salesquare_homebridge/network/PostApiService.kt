package com.example.app_salesquare_homebridge.network

import com.example.app_salesquare_homebridge.communication.PublicationResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PostApiService {
    @GET("api/v1/publication/getPublication")
    fun getPublication(@Query("id") id: Int): Call<List<PublicationResponse>>
}