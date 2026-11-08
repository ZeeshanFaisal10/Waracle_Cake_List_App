package com.example.waraclecakelistapp.data.repository

import com.example.waraclecakelistapp.domain.model.Cake

interface CakeRepository {
    suspend fun getCakes(): List<Cake>
}