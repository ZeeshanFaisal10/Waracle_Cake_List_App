package com.example.waraclecakelistapp.data.remote

import com.google.gson.annotations.SerializedName

data class CakeDto(
    @SerializedName("title")
    val title: String,

    @SerializedName("desc")
    val description: String,

    @SerializedName("image")
    val imageUrl: String

)