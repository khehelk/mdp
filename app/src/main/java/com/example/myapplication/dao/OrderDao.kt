package com.example.myapplication.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.model.Order
import com.example.myapplication.model.OrderService
import com.example.myapplication.model.OrderWithServices
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    @Insert
    suspend fun createOrder(order: Order):Long

    @Insert
    suspend fun insertOrderService(orderService: OrderService)

    @Query("SELECT * FROM tbl_order WHERE orderId = :id")
    fun getOrderWithServices(id: Int): Flow<OrderWithServices>

    @Query("SELECT * FROM tbl_order")
    fun getAllOrder(): Flow<List<Order>>

    @Delete
    suspend fun delete(order: Order)
}