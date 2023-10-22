package com.example.mystore.model

data class DataResponse(
    val status: Boolean,
    val message: String,
    val error: Any?, // You can replace "Any?" with a more specific type if needed
    val categories: List<Category>
)

data class Category(
    val id: Int,
    val name: String,
    val items: List<Item>
)

data class Item(
    val id: Int,
    val name: String,
    val icon: String,
    val price: Double
)
