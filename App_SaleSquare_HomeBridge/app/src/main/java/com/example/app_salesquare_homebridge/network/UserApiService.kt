package com.example.app_salesquare_homebridge.network

import com.example.app_salesquare_homebridge.communication.UserRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApiService {
    @POST("user/register")
    fun registerUser(@Body userRequest: UserRequest): Call<Void>
}