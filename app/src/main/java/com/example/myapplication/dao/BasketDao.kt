package com.example.myapplication.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.model.Basket
import com.example.myapplication.model.BasketService
import com.example.myapplication.model.BasketWithServices
import kotlinx.coroutines.flow.Flow

@Dao
interface BasketDao {

    @Insert
    suspend fun createBasket(basket: Basket):Long

    @Insert
    suspend fun insertBasketService(basketService: BasketService)

    @Query("SELECT * FROM tbl_basket WHERE creatorUserId = :id")
    fun getBasketWithServices(id: Int): Flow<BasketWithServices>

    @Query("SELECT * FROM tbl_basket")
    fun getAllBasket(): Flow<List<Basket>>

    @Delete
    suspend fun delete(basket: Basket)
}