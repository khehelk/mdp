package com.example.myapplication.database.repository

import com.example.myapplication.businessLogic.repository.OrderRepository
import com.example.myapplication.database.dao.OrderDao
import com.example.myapplication.model.Order
import com.example.myapplication.model.OrderService
import com.example.myapplication.model.Service
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class OrderRepositoryImpl(private val orderDao: OrderDao): OrderRepository {
    override suspend fun insert(order: Order) = orderDao.insert(order)
    override suspend fun addServiceToOrder(orderService: OrderService) = orderDao.insertOrderService(orderService)
    override suspend fun delete(order: Order) = orderDao.delete(order)
    override suspend fun getServiceFromOrder(id: Int): Flow<List<Service>> {
        TODO("Not yet implemented")
    }
    suspend fun getOrderWithServices(id: Int) = orderDao.getOrderWithServices(id)
    suspend fun getAllOrders() = orderDao.getAllOrders()
    override suspend fun getUserOrders(id: Int): Flow<List<Order>> = flow { orderDao.getUserOrders(id).first().orders }
}