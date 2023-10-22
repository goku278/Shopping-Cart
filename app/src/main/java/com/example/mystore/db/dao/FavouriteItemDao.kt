package com.example.mystore.db.dao

import androidx.room.*
import com.example.mystore.db.entity.FavouriteItem

@Dao
interface FavouriteItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteItem(favouriteItem: FavouriteItem)

    @Query("SELECT * FROM favourite_item")
    suspend fun getFavouriteItems(): List<FavouriteItem>

    @Delete
    suspend fun deleteFavouriteItem(item: FavouriteItem)
}