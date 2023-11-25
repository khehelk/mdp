package com.example.myapplication.repository

import com.example.myapplication.dao.OrderDao
import com.example.myapplication.model.Order
import com.example.myapplication.model.OrderService

class OrderRepository(private val orderDao: OrderDao) {
    suspend fun insert(order: Order) = orderDao.insert(order)
    suspend fun insertOrderService(orderService: OrderService) = orderDao.insertOrderService(orderService)
    suspend fun delete(order: Order) = orderDao.delete(order)
    suspend fun getOrderWithServices(id: Int) = orderDao.getOrderWithServices(id)
    suspend fun getAllOrders() = orderDao.getAllOrders()
}