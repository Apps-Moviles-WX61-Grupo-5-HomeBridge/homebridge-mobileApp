package com.example.app_salesquare_homebridge.network

import com.example.app_salesquare_homebridge.communication.PublicationResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeBridgeApiService {
    @GET("publication/{id}")
    fun getPublications(@Path("id") id: Int): Call<PublicationResponse>
}