package com.example.mystore.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mystore.db.entity.Cart

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cart: Cart)
    @Query("SELECT * FROM cart")
    suspend fun getCart(): List<Cart>
    @Update
    suspend fun update(cart: Cart)
    @Query("SELECT * FROM cart WHERE itemId = :itemId")
    suspend fun getCartDataByItemId(itemId: Int): Cart
}