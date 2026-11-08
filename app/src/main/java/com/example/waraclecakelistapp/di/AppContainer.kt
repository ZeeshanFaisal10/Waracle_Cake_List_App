package com.example.waraclecakelistapp.di

import com.example.waraclecakelistapp.data.remote.CakeApiService
import com.example.waraclecakelistapp.data.repository.CakeRepository
import com.example.waraclecakelistapp.data.repository.CakeRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO: Make the API base URL configurable if separate
// For example: development, testing, and production environments are introduced.
class AppContainer {
    private val retrofit: Retrofit = Retrofit.Builder().baseUrl(
        "https://raw.githubusercontent.com/Waracle/mobile-coding-test-api/refs/heads/main/"
    ).addConverterFactory(GsonConverterFactory.create()).build()

    private val cakeApiService: CakeApiService = retrofit.create(CakeApiService::class.java)

    val cakeRepository: CakeRepository = CakeRepositoryImpl(cakeApiService)
}