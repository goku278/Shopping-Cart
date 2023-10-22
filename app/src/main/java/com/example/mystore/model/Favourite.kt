package com.example.mystore.model

import com.google.gson.annotations.SerializedName

data class Favourite(
    @SerializedName("name") val name: Boolean,
    @SerializedName("image") val image: String,
    @SerializedName("qty") val qty: String?,
    @SerializedName("price") val price: String?
)