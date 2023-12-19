package com.example.myapplication.businessLogic.repository

import com.example.myapplication.model.Order
import com.example.myapplication.model.OrderService
import com.example.myapplication.model.Service
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun insert(order: Order): Long
    suspend fun addServiceToOrder(orderService: OrderService)
    suspend fun delete(order: Order)
    suspend fun getServiceFromOrder(id: Int): Flow<List<Service>>
    suspend fun getUserOrders(id: Int) : Flow<List<Order>>
}