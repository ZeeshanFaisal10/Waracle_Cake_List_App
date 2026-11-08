package com.example.waraclecakelistapp.data.repository

import com.example.waraclecakelistapp.domain.model.Cake

class FakeCakeRepository(
    private val fetchCakes: suspend () -> List<Cake>
) : CakeRepository {

    override suspend fun getCakes(): List<Cake> = fetchCakes()
}