package com.example.mystore.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Entity, and its name is Category
// It will store the data in the room database, and its table name is category

@Entity(tableName = "category")
data class Category(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    val id: Int,
    @ColumnInfo(name="name")
    val name: String
) {
    override fun toString(): String {
        return "Category(id=$id, name='$name')"
    }
}