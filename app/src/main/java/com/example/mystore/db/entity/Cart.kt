package com.example.mystore.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Entity, and its name is Cart
// It will store the data in the room database, and its table name is cart

@Entity(tableName = "cart")
data class Cart(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "icon")
    val icon: String,
    @ColumnInfo(name = "qty")
    var qty: String,
    @ColumnInfo(name = "price")
    val price: String,
    @ColumnInfo(name = "itemId")
    val itemId: Int?

) {
    override fun toString(): String {
        return "Cart(id=$id, name='$name', icon='$icon', qty='$qty', price='$price', itemId='$itemId')"
    }
}