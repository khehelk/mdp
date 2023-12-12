package com.example.myapplication.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.model.Order
import com.example.myapplication.model.OrderService
import com.example.myapplication.model.OrderWithServices
import com.example.myapplication.model.UserWithOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Insert
    suspend fun insert(order: Order):Long
    @Delete
    suspend fun delete(order: Order)
    @Insert
    suspend fun insertOrderService(orderService: OrderService)
    @Query("SELECT * FROM tbl_order WHERE orderId = :id")
    fun getOrderWithServices(id: Int): Flow<OrderWithServices>
    @Query("SELECT * FROM tbl_order")
    fun getAllOrders(): Flow<List<Order>>
    @Query("SELECT * FROM tbl_user WHERE userId =:id")
    fun getUserOrders(id: Int) : Flow<UserWithOrder>
}