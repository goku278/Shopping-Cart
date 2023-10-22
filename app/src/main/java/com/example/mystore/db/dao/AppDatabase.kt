package com.example.mystore.db.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mystore.db.entity.Cart
import com.example.mystore.db.entity.Category
import com.example.mystore.db.entity.FavouriteItem
import com.example.mystore.db.entity.Item

@Database(
    entities = [Category::class, Item::class, FavouriteItem::class, Cart::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun itemDao(): ItemDao
    abstract fun favouriteItemDao(): FavouriteItemDao
    abstract fun cartDao(): CartDao
}
