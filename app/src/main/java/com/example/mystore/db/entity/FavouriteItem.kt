package com.example.mystore.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Entity, and its name is FavouriteItem
// It will store the data in the room database, and its table name is favourite_item

@Entity(tableName = "favourite_item")
data class FavouriteItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id: Int,
    @ColumnInfo(name="name")
    val name: String,
    @ColumnInfo(name="icon")
    val icon: String,
    @ColumnInfo(name="qty")
    val qty: String,
    @ColumnInfo(name="price")
    val price: String
) {
    override fun toString(): String {
        return "FavouriteItem(id=$id, name='$name', icon='$icon', qty='$qty', price='$price')"
    }
}