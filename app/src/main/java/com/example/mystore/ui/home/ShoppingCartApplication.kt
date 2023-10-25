package com.example.mystore.ui.home

import android.app.Application
import androidx.room.Room
import com.example.mystore.db.dao.AppDatabase

class ShoppingCartApplication : Application() {
    val database by lazy { Room.databaseBuilder(this, AppDatabase::class.java, "shopping_cart_db").build() }
}
