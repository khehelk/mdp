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
    suspend fun insert(basket: Basket):Long
    @Insert
    suspend fun insertBasketService(basketService: BasketService)
    @Query("SELECT * FROM tbl_basket WHERE creatorUserId = :id")
    fun getBasketWithServices(id: Int): Flow<BasketWithServices>
    @Query("SELECT * FROM tbl_basket WHERE creatorUserId = :id")
    suspend fun getUsersBasket(id: Int): Basket
    @Query("SELECT * FROM tbl_basket")
    fun getAllBaskets(): Flow<List<Basket>>
    @Delete
    suspend fun delete(basket: Basket)
    @Query("DELETE FROM tbl_basket_service WHERE basketId = :basketId AND serviceId = :serviceId")
    suspend fun removeServiceFromBasket(basketId: Int, serviceId: Int)
    @Query("UPDATE tbl_basket_service SET quantity = :quantity WHERE basketId = :basketId AND serviceId = :serviceId")
    suspend fun updateServiceQuantity(basketId: Int, serviceId: Int, quantity: Int)
    @Query("UPDATE tbl_basket_service SET quantity = quantity + 1 WHERE basketId = :basketId AND serviceId = :serviceId")
    suspend fun incrementServiceQuantity(basketId: Int, serviceId: Int)
    @Query("UPDATE tbl_basket_service SET quantity = quantity - 1 WHERE basketId = :basketId AND serviceId = :serviceId")
    suspend fun decrementServiceQuantity(basketId: Int, serviceId: Int)
    @Query("SELECT quantity FROM tbl_basket_service WHERE basketId = :basketId AND serviceId = :serviceId")
    suspend fun getQuantity(basketId: Int, serviceId: Int): Int?
    @Query("SELECT * FROM tbl_basket_service WHERE basketId = :basketId AND serviceId = :serviceId")
    suspend fun getService(basketId: Int, serviceId: Int): BasketService?
    @Query("SELECT SUM(price * quantity) FROM tbl_basket_service bs JOIN tbl_service s ON bs.serviceId = s.serviceId WHERE bs.basketId = :basketId")
    suspend fun getTotalPriceForBasket(basketId: Int): Double?
    @Query("DELETE FROM tbl_basket_service WHERE basketId = :basketId")
    suspend fun clearBasket(basketId: Int)
}