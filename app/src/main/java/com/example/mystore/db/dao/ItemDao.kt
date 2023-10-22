package com.example.mystore.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mystore.db.entity.Cart
import com.example.mystore.db.entity.Item

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: Item)

    @Query("SELECT * FROM item WHERE categoryId = :categoryId")
    suspend fun getItemsForCategory(categoryId: Int): List<Item>
    @Update
    suspend fun update(item: Item)
    @Query("SELECT * FROM item")
    suspend fun getItems(): List<Item>
    @Query("SELECT * FROM item WHERE name = :name AND icon = :icon")
    suspend fun findRecordByNameIcon(name: String, icon: String): Item?
    @Query("SELECT * FROM item WHERE is_favourite = :isFavourite")
    suspend fun findAllItemsWithIsFavourite(isFavourite: String): Item?
    // Add other queries as needed
}