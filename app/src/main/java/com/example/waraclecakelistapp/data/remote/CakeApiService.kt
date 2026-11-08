package com.example.waraclecakelistapp.data.remote

import retrofit2.http.GET

interface CakeApiService {

    @GET("cakes")
    suspend fun getCakes(): List<CakeDto>
}