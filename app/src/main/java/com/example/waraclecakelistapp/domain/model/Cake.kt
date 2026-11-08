package com.example.waraclecakelistapp.domain.model


data class Cake(
    val title: String,
    val description: String,
    val imageUrl: String
)

// TODO: Add a unique cake ID if the API provides one in the future,
//       allowing more reliable identification of duplicate cakes.