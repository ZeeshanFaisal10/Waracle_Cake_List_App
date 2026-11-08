package com.example.waraclecakelistapp.data.mapper

import com.example.waraclecakelistapp.data.remote.CakeDto
import com.example.waraclecakelistapp.domain.model.Cake

// TODO: Consider validating API fields during DTO mapping
// if future API responses contain missing or malformed values.
fun CakeDto.toDomain(): Cake{
    return Cake(
        title = title,
        description = description,
        imageUrl = imageUrl,
    )
}