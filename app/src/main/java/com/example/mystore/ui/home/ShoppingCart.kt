package com.example.mystore.ui.home

import android.text.BoringLayout
import com.example.mystore.db.entity.Cart
import com.example.mystore.db.entity.FavouriteItem
import com.example.mystore.db.entity.Item

object ShoppingCart {

    // This is a ShoppingCart object file
    // Which has an interface Listener
    // And also a method through which we are binding the listener from the MainActivity
    // Passing the Activity's context to the setListenerToFetchAndStoreData() in it's method's parameter.

    private var fetchStoreData: Listener? = null

    interface Listener {
        fun onDataFetched(data: ArrayList<ArrayList<Item>>)
        fun onCartDataFetched(cartData: ArrayList<Cart>)
        fun onFavouriteDataFetched(data: ArrayList<FavouriteItem>)
        fun onCountCartItems(total: Int?)
        fun onTableRecordsFound(isFound: Boolean?)
    }

    fun setListenerToFetchAndStoreData(listener: Listener) {
        fetchStoreData = listener
    }
}