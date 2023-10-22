package com.example.mystore.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Entity, and its name is Item
// It will store the data in the room database, and its table name is item

@Entity(tableName = "item")
data class Item(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    val id: Int,
    @ColumnInfo(name="name")
    val name: String,
    @ColumnInfo(name="icon")
    val icon: String,
    @ColumnInfo(name="price")
    val price: Double,
    @ColumnInfo(name="categoryId")
    val categoryId: Int, // Foreign key to Category
    @ColumnInfo(name="is_favourite")
    var isFavourite: String,
) {
    override fun toString(): String {
        return "Item(id=$id, name='$name', icon='$icon', price=$price, categoryId=$categoryId, isFavourite=$isFavourite)"
    }
}