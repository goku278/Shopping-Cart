package com.example.mystore.model

import android.widget.ImageView

data class DataModel(
    val id: String?,
    val name: String?,
    val price: String?,
    val icon: String?,
    var addToCart: String?,
    var isFavourite: String?,
    var qty: String?,
) {
    override fun toString(): String {
        return "DataModel(id=$id,name=$name, price=$price, icon=$icon, addToCart=$addToCart, isFavourite=$isFavourite, qty=$qty)"
    }
}