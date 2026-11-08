package com.example.waraclecakelistapp.data.repository

import com.example.waraclecakelistapp.data.mapper.toDomain
import com.example.waraclecakelistapp.data.remote.CakeApiService
import com.example.waraclecakelistapp.domain.model.Cake

class CakeRepositoryImpl(
    private val apiService: CakeApiService
): CakeRepository {
    override suspend fun getCakes(): List<Cake> {
        return apiService.getCakes().map { it.toDomain() }
    }

}