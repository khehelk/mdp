package com.example.myapplication.database.repository

import com.example.myapplication.database.dao.OrderDao
import com.example.myapplication.model.Order
import com.example.myapplication.model.OrderService

class OrderRepository(private val orderDao: OrderDao) {
    suspend fun insert(order: Order) = orderDao.insert(order)
    suspend fun insertOrderService(orderService: OrderService) = orderDao.insertOrderService(orderService)
    suspend fun delete(order: Order) = orderDao.delete(order)
    fun getOrderWithServices(id: Int) = orderDao.getOrderWithServices(id)
    fun getAllOrders() = orderDao.getAllOrders()
    fun getUserOrders(id: Int) = orderDao.getUserOrders(id)
}